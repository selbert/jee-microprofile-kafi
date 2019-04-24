package ch.puzzle.selbert.jee.kafi.shop.boundary;

import ch.puzzle.selbert.jee.kafi.shop.control.Inventory;
import ch.puzzle.selbert.jee.kafi.shop.entity.Item;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@RequestScoped
@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemsResource {

    @Inject
    Inventory inventory;

    @Context
    UriInfo uriInfo;

    @GET
    @Metered(name = "itemListMeter")
    @Counted(name = "itemListCount", monotonic = true)
    public List<Item> itemList() {
        return inventory.all();
    }

    @GET
    @Path("/{id}")
    @Metered(name = "itemMeter")
    @Counted(name = "itemCount", monotonic = true)
    public Item item(@PathParam(value = "id") int id) {
        return inventory.getItem(id);
    }

    @POST
    @Metered(name = "addItemMeter")
    @Counted(name = "addItemCount", monotonic = true)
    public Response addItem(Item item) {
        System.out.println(item.id + " - " + item.name);
        return inventory.storeItem(item)
                .map(i -> uriInfo.getAbsolutePathBuilder().path(i.id + ""))
                .map(builder -> Response
                        .created(builder.build())
                        .links(Link.fromUriBuilder(builder).rel("self").build())
                        .build())
                .orElse(Response
                        .status(Response.Status.SERVICE_UNAVAILABLE)
                        .build());
    }
}
