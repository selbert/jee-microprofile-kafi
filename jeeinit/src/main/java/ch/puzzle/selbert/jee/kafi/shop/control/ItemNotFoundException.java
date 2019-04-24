package ch.puzzle.selbert.jee.kafi.shop.control;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ItemNotFoundException extends WebApplicationException {

    public ItemNotFoundException(int id) {
        super(Response.status(400).header("reason", "message " + id + " not found").build());
    }
}
