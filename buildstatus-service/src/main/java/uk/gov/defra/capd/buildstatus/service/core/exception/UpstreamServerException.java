package uk.gov.defra.capd.buildstatus.service.core.exception;

import com.sun.jersey.api.Responses;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class UpstreamServerException extends WebApplicationException {

    /**
     * Create a HTTP 502 (server received an invalid response from upstream server) exception.
     */
    public UpstreamServerException() {
        super(Responses.conflict().build());
    }

    /**
     * Create a HTTP 502 (server received an invalid response from upstream server) exception.
     *
     * @param message the String that is the entity of the 502 response.
     */
    public UpstreamServerException(String message) {
        super(Response.status(502).entity(message).type("text/plain").build());
    }
}