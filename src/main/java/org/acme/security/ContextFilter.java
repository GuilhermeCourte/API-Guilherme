package org.acme.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@Priority(Priorities.HEADER_DECORATOR) // Run early
public class ContextFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Context
    private HttpHeaders headers;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        JaxRsContextHolder.setHeaders(headers);
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        JaxRsContextHolder.clear();
    }
}
