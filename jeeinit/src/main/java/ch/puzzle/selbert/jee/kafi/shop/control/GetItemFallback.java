package ch.puzzle.selbert.jee.kafi.shop.control;

import ch.puzzle.selbert.jee.kafi.shop.entity.Item;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

public class GetItemFallback implements FallbackHandler<Item> {
    @Override
    public Item handle(ExecutionContext executionContext) {
        int id = (int) executionContext.getParameters()[0];
        System.out.println("Falled back for id " + id);
        Item f = new Item();
        f.id = id;
        f.name = "fallback";
        return f;
    }
}
