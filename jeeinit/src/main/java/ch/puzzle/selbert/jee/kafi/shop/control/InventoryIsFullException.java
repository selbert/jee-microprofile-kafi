package ch.puzzle.selbert.jee.kafi.shop.control;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class InventoryIsFullException extends WebApplicationException {

    public InventoryIsFullException(String message) {
        super(Response.status(400).header("reason", message).build());
    }

}