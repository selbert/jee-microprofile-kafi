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
import java.util.Optional;

@ApplicationScoped
public class Inventory {

    @Inject
    @ConfigProperty(name = "should.fail", defaultValue = "false")
    Boolean shouldFail;

    @Inject
    @ConfigProperty(name = "max.size", defaultValue = "3")
    int maxSize;

    private List<Item> items;

    @PostConstruct
    public void init() {
        this.items = new ArrayList<>();
    }

    @Fallback(fallbackMethod = "trashTheItem")
    @Retry(maxRetries = 2)
    public Optional<Item> storeItem(Item item) {

        if (shouldFail && itemsNumber() > maxSize) {
            System.out.println("....retrying...");
            throw new InventoryIsFullException("Store is full");
        }
        this.items.add(item);
        return Optional.of(item);
    }

    public Optional<Item> trashTheItem(Item egg) {
        System.out.println("--- throwing the item away: " + egg);
        return Optional.empty();
    }

    @Gauge(unit = MetricUnits.NONE)
    public int itemsNumber() {
        return this.items.size();
    }

    public List<Item> all() {
        return this.items;
    }
    
    @Fallback(GetItemFallback.class)
    public Item getItem(int id) {
        return this.items.stream()
                .filter(item -> Objects.equals(id, item.id))
                .findAny()
                .orElseThrow(() -> new ItemNotFoundException(id));
    }
}
