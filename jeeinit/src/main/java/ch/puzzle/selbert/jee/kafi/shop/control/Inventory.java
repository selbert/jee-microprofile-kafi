package ch.puzzle.selbert.jee.kafi.shop.control;

import ch.puzzle.selbert.jee.kafi.shop.entity.Item;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@ApplicationScoped
public class Inventory {
    private List<Item> items;
    private Random random = new Random();
    @Inject
    @ConfigProperty(name = "should.fail", defaultValue = "false")
    Boolean shouldFail;

    @PostConstruct
    public void init() {
        this.items = new ArrayList<>();
    }

    @Fallback(fallbackMethod = "trashTheItem")
    @Retry(maxRetries = 2)
    public void storeItem(Item egg) {
        System.out.println("....retrying...");
        if (shouldFail && random.nextBoolean())
            throw new InventoryIsFullException("Store is full");
        this.items.add(egg);
    }

    public void trashTheItem(Item egg) {
        System.out.println("--- throwing the egg away: " + egg);
    }

    @Gauge(unit = MetricUnits.NONE)
    public int itemsNumber() {
        return this.items.size();
    }

    public List<Item> all() {
        return this.items;
    }

    public Item getItem(int id) {
        return this.items.stream()
                .filter(item -> Objects.equals(id,item.id))
                .findAny()
                .orElseThrow(() -> new ItemNotFoundException(id));
    }
}