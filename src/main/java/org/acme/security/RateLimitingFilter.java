package org.acme.security;

import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import jakarta.annotation.Priority;

@Provider
@ApiKeyRequired
@Priority(Priorities.AUTHORIZATION)
public class RateLimitingFilter implements ContainerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-KEY";

    @Inject
    RateLimitingService rateLimitingService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String apiKey = requestContext.getHeaderString(API_KEY_HEADER);

        // The AuthenticationFilter should have already validated the key,
        // but we check again for safety.
        if (apiKey == null || apiKey.isBlank()) {
            return; // Let AuthenticationFilter handle it.
        }

        int requestCount = rateLimitingService.incrementRequestCount(apiKey);

        int remaining = RateLimitingService.LIMIT - requestCount;

        requestContext.getHeaders().add("X-RateLimit-Limit", String.valueOf(RateLimitingService.LIMIT));
        requestContext.getHeaders().add("X-RateLimit-Remaining", String.valueOf(Math.max(0, remaining)));
        // The reset time is implicitly handled by the cache's expiry policy (1 minute after last write).
        // A more precise X-RateLimit-Reset would require more complex cache logic.

        if (requestCount > RateLimitingService.LIMIT) {
            requestContext.abortWith(
                    Response.status(Response.Status.TOO_MANY_REQUESTS)
                            .entity("You have exceeded your request limit.")
                            .build()
            );
        }
    }
}
