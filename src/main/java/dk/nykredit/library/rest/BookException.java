package dk.nykredit.library.rest;

import javax.ws.rs.core.Response;

public class BookException extends Exception {
    private final Response.Status status;

    public BookException(String message, Response.Status status) {
        super(message);
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }
}
