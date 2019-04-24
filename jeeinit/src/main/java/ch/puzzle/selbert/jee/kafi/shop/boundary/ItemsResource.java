package ch.puzzle.selbert.jee.kafi.shop.boundary;

import ch.puzzle.selbert.jee.kafi.shop.entity.Item;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
public class ItemsResource {
    @Inject
    private JsonWebToken callerPrincipal;

    @GET
    public List<Item> itemList() {
        System.out.println(callerPrincipal.getIssuer());
        System.out.println(callerPrincipal.getRawToken());
        System.out.println(callerPrincipal.getTokenID());
        System.out.println(callerPrincipal.getGroups());
        Item theItem = new Item();
        theItem.id = "this item's id";
        return List.of(theItem);
    }

    @GET
    @Path("/{id}")
    public Item item(@PathParam(value = "id") String id) {
        Item theItem = new Item();
        theItem.id = id;
        return theItem;
    }
}
