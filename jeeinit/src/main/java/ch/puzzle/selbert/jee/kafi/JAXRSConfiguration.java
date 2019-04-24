package ch.puzzle.selbert.jee.kafi;

import org.eclipse.microprofile.auth.LoginConfig;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@LoginConfig(authMethod = "MP-JWT")
@ApplicationPath("resources")
@DeclareRoles({"USER", "ADMIN", "puzzle"})
public class JAXRSConfiguration extends Application {

}