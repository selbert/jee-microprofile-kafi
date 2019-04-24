package ch.puzzle.selbert.jee.kafi;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("resources")
@DeclareRoles({"USER", "ADMIN", "puzzle"})
public class JAXRSConfiguration extends Application {

}