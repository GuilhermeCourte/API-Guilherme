package org.acme;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.time.LocalDate;

@Path("/apikeys")
public class ApiKeyResource {

    @POST
    @Transactional
    @Operation(
        summary = "Cria uma nova chave de API",
        description = "Cria uma nova chave de API com uma chave e uma descrição."
    )
    @RequestBody(
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiKey.class)
        )
    )
    @APIResponse(
        responseCode = "201",
        description = "Chave de API criada com sucesso",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiKey.class)
        )
    )
    public Response createApiKey(ApiKey apiKey) {
        if (apiKey.apiKey == null || apiKey.apiKey.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("A chave (apiKey) da API não pode ser vazia.").build();
        }
        if (ApiKey.findById(apiKey.apiKey) != null) {
            return Response.status(Response.Status.CONFLICT).entity("A chave de API já existe.").build();
        }
        apiKey.creationDate = LocalDate.now();
        ApiKey.persist(apiKey);
        return Response.status(Response.Status.CREATED).entity(apiKey).build();
    }
}
