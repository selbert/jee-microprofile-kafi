package ch.puzzle.selbert.jee.kafi;

import org.eclipse.microprofile.auth.LoginConfig;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("resources")
@LoginConfig(authMethod = "MP-JWT")
@DeclareRoles({"admin"})
public class JAXRSConfiguration extends Application {

}