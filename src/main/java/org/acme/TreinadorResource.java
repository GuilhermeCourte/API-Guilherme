package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.acme.security.ApiKeyRequired;
import org.acme.security.Idempotent;

import org.acme.dto.TreinadorSearchResponse;
import java.util.List;
import java.util.Set;

@Path("/treinadores")
public class TreinadorResource {
    @GET
    @Operation(
            summary = "Retorna todos os treinadores/figuras do boxe (getAll)",
            description = "Retorna uma lista de treinadores ou figuras importantes do boxe no formato JSON"
    )
    @APIResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Treinador.class, type = SchemaType.ARRAY)
            )
    )
    public Response getAll(){
        return Response.ok(Treinador.listAll()).build();
    }

    @GET
    @Path("{id}")
    @Operation(
            summary = "Retorna um treinador/figura do boxe pela busca por ID (getById)",
            description = "Retorna um treinador ou figura do boxe específica pela busca de ID colocado na URL no formato JSON por padrão"
    )
    @APIResponse(
            responseCode = "200",
            description = "Item retornado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Treinador.class, type = SchemaType.ARRAY)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Item não encontrado",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class))
    )
    public Response getById(
            @Parameter(description = "Id do treinador/figura do boxe a ser pesquisado", required = true)
            @PathParam("id") long id){
        Treinador entity = Treinador.findById(id);
        if(entity == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(entity).build();
    }

    @GET
    @Operation(
            summary = "Retorna treinadores/figuras do boxe conforme o sistema de pesquisa (search)",
            description = "Retorna uma lista de treinadores ou figuras do boxe filtrada conforme a pesquisa por padrão no formato JSON"
    )
    @APIResponse(
            responseCode = "200",
            description = "Item retornado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TreinadorSearchResponse.class)
            )
    )
    @Path("/search")
    public Response search(
            @Parameter(description = "Query de buscar por nome ou nacionalidade do treinador/figura do boxe")
            @QueryParam("q") String q,
            @Parameter(description = "Campo de ordenação da lista de retorno")
            @QueryParam("sort") @DefaultValue("id") String sort,
            @Parameter(description = "Esquema de filtragem de diretores por ordem crescente ou decrescente")
            @QueryParam("direction") @DefaultValue("asc") String direction,
            @Parameter(description = "Define qual página será retornada na response")
            @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Define quantos objetos serão retornados por query")
            @QueryParam("size") @DefaultValue("4") int size
    ){
        Set<String> allowed = Set.of("id", "nome", "nascimento", "nacionalidade");
        if(!allowed.contains(sort)){
            sort = "id";
        }

        Sort sortObj = Sort.by(
                sort,
                "desc".equalsIgnoreCase(direction) ? Sort.Direction.Descending : Sort.Direction.Ascending
        );

        int effectivePage = Math.max(page, 0);

        PanacheQuery<Treinador> query;

        if (q == null || q.isBlank()) {
            query = Treinador.findAll(sortObj);
        } else {
            query = Treinador.find(
                    "lower(nome) like ?1 or lower(nacionalidade) like ?1", sortObj, "%" + q.toLowerCase() + "%");
        }

        long totalElements = query.count();
        int totalPages = query.pageCount();
        List<Treinador> treinadores = query.page(effectivePage, size).list();

        boolean hasMore = effectivePage < totalPages - 1;
        String nextPage = hasMore ? String.format("/treinadores/search?q=%s&sort=%s&direction=%s&page=%d&size=%d",
                q != null ? q : "", sort, direction, effectivePage + 1, size) : null;

        var response = new TreinadorSearchResponse(treinadores, totalElements, totalPages, hasMore, nextPage);

        return Response.ok(response).build();
    }

    @POST
    @Idempotent
    @ApiKeyRequired
    @Operation(
            summary = "Adiciona um registro a lista de treinadores/figuras do boxe (insert)",
            description = "Adiciona um item a lista de treinadores ou figuras do boxe por meio de POST e request body JSON"
    )
    @RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Treinador.class)
            )
    )
    @APIResponse(
            responseCode = "201",
            description = "Created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Treinador.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class))
    )
    @Transactional
    public Response insert(@Valid Treinador treinador){
        Treinador.persist(treinador);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @ApiKeyRequired
    @Operation(
            summary = "Remove um registro da lista de treinadores/figuras do boxe (delete)",
            description = "Remove um item da lista de treinadores ou figuras do boxe por meio de Id na URL"
    )
    @APIResponse(
            responseCode = "204",
            description = "Sem conteúdo",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class))
    )
    @APIResponse(
            responseCode = "404",
            description = "Item não encontrado",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class))
    )
    @APIResponse(
            responseCode = "409",
            description = "Conflito - Treinador/figura do boxe possui filmes/lutas vinculados",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class))
    )
    @Transactional
    @Path("{id}")
    public Response delete(@PathParam("id") long id){
        Treinador entity = Treinador.findById(id);
        if(entity == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        long lutadoresVinculados = Lutador.count("treinador.id = ?1", id);
        if(lutadoresVinculados > 0){
            return Response.status(Response.Status.CONFLICT)
                    .entity("Não é possível deletar treinador. Existem " + lutadoresVinculados + " lutador(es) vinculado(s).")
                    .build();
        }

        Treinador.deleteById(id);
        return Response.noContent().build();
    }

    @PUT
    @ApiKeyRequired
    @Operation(
            summary = "Altera um registro da lista de treinadores/figuras do boxe (update)",
            description = "Edita um item da lista de treinadores ou figuras do boxe por meio de Id na URL e request body JSON"
    )
    @RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Treinador.class)
            )
    )
    @APIResponse(
            responseCode = "200",
            description = "Item editado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Treinador.class, type = SchemaType.ARRAY)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Item não encontrado",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class))
    )
    @Transactional
    @Path("{id}")
    public Response update(@PathParam("id") long id, @Valid Treinador newTreinador){
        Treinador entity = Treinador.findById(id);
        if(entity == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entity.nome = newTreinador.nome;
        entity.nascimento = newTreinador.nascimento;
        entity.nacionalidade = newTreinador.nacionalidade;
        // Atualizar biografia (será criada automaticamente se não existir devido ao CascadeType.ALL)
        if(newTreinador.biografia != null){
            if(entity.biografia == null){
                entity.biografia = new BiografiaTreinador();
            }
            entity.biografia.textoCompleto = newTreinador.biografia.textoCompleto;
            entity.biografia.resumo = newTreinador.biografia.resumo;
            entity.biografia.premiosRecebidos = newTreinador.biografia.premiosRecebidos;
        } else {
            // Se não vier biografia no request, limpa a biografia existente
            entity.biografia = null;
        }

        return Response.status(Response.Status.OK).entity(entity).build();
    }
}
