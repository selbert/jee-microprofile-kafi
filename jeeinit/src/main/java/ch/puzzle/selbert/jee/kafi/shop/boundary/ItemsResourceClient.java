package ch.puzzle.selbert.jee.kafi.shop.boundary;

import ch.puzzle.selbert.jee.kafi.shop.entity.Item;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient
@Path("/resources/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ItemsResourceClient {
    @GET
    List<Item> items();
}
