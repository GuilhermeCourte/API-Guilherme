package org.acme.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.dto.ErrorResponse;

import java.util.Collections;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        // Log the exception for debugging purposes
        exception.printStackTrace();

        ErrorResponse errorResponse = new ErrorResponse(
                "Ocorreu um erro inesperado no servidor.",
                Collections.singletonList(exception.getMessage())
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    }
}
