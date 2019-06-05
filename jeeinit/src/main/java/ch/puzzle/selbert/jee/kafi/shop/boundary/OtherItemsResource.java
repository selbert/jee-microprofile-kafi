package ch.puzzle.selbert.jee.kafi.shop.boundary;

import ch.puzzle.selbert.jee.kafi.shop.entity.Item;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.List;

@RequestScoped
@Path("/other")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OtherItemsResource {

    @Inject
    @ConfigProperty(name = "other.service", defaultValue = "http://shop-ol:9080/shop")
    String otherServiceUrl;

    @GET
    @Path("/items")
    public List<Item> getItemsFromOtherService() {
        ItemsResourceClient client = RestClientBuilder.newBuilder()
                .baseUri(URI.create(otherServiceUrl))
                .build(ItemsResourceClient.class);
        return client.items();
    }

}
