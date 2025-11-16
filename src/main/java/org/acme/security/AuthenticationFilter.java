package org.acme.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.acme.ApiKey;

@Provider
@ApiKeyRequired
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-KEY";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String apiKey = requestContext.getHeaderString(API_KEY_HEADER);

        if (apiKey == null || apiKey.isBlank()) {
            abortWithUnauthorized(requestContext, "API key is missing.");
            return;
        }

        if (ApiKey.findById(apiKey) == null) {
            abortWithUnauthorized(requestContext, "Invalid API key.");
            return;
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext, String message) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity(message)
                        .build()
        );
    }
}
