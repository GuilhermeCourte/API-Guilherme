package org.acme;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/apikeys")
public class ApiKeyResource {

    @POST
    @Transactional
    public Response generateApiKey(@QueryParam("clientName") String clientName) {
        if (clientName == null || clientName.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("clientName query parameter is required.")
                    .build();
        }
        ApiKey newKey = ApiKey.generate(clientName);
        ApiKey.persist(newKey);
        return Response.status(Response.Status.CREATED).entity(newKey).build();
    }
}
