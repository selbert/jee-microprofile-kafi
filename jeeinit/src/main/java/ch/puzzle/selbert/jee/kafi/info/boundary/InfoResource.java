package ch.puzzle.selbert.jee.kafi.info.boundary;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Map;

@RequestScoped
@Path("/info")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InfoResource {

    @Inject
    @ConfigProperty(name = "application.server", defaultValue = "Not Specified")
    String applicationServer;

    @GET
    public Map<String, String> getInfo() {
        return Collections.singletonMap("applicationServer", applicationServer);
    }


}
