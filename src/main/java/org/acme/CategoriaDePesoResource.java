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

import org.acme.dto.CategoriaDePesoSearchResponse;
import java.util.List;
import java.util.Set;

@Path("/categoriasdepeso")
public class CategoriaDePesoResource {
    @GET
    @Operation(
            summary = "Retorna todas as categorias de peso (getAll)",
            description = "Retorna uma lista de categorias de peso no formato JSON"
    )
    @APIResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaDePeso.class, type = SchemaType.ARRAY)
            )
    )
    public Response getAll(){
        return Response.ok(CategoriaDePeso.listAll()).build();
    }

    @GET
    @Path("{id}")
    @Operation(
            summary = "Retorna uma categoria de peso pela busca por ID (getById)",
            description = "Retorna uma categoria de peso específica pela busca de ID colocado na URL no formato JSON por padrão"
    )
    @APIResponse(
            responseCode = "200",
            description = "Item retornado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaDePeso.class, type = SchemaType.ARRAY)
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
            @Parameter(description = "Id da categoria de peso a ser pesquisada", required = true)
            @PathParam("id") long id){
        CategoriaDePeso entity = CategoriaDePeso.findById(id);
        if(entity == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(entity).build();
    }

    @GET
    @Operation(
            summary = "Retorna categorias de peso conforme o sistema de pesquisa (search)",
            description = "Retorna uma lista de categorias de peso filtrada conforme a pesquisa por padrão no formato JSON"
    )
    @APIResponse(
            responseCode = "200",
            description = "Item retornado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaDePesoSearchResponse.class)
            )
    )
    @Path("/search")
    public Response search(
            @Parameter(description = "Query de buscar por nome da categoria de peso")
            @QueryParam("q") String q,
            @Parameter(description = "Campo de ordenação da lista de retorno")
            @QueryParam("sort") @DefaultValue("id") String sort,
            @Parameter(description = "Esquema de filtragem de categorias de peso por ordem crescente ou decrescente")
            @QueryParam("direction") @DefaultValue("asc") String direction,
            @Parameter(description = "Define qual página será retornada na response")
            @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Define quantos objetos serão retornados por query")
            @QueryParam("size") @DefaultValue("4") int size
    ){
        Set<String> allowed = Set.of("id", "nome", "descricao");
        if(!allowed.contains(sort)){
            sort = "id";
        }

        Sort sortObj = Sort.by(
                sort,
                "desc".equalsIgnoreCase(direction) ? Sort.Direction.Descending : Sort.Direction.Ascending
        );

        int effectivePage = Math.max(page, 0);

        PanacheQuery<CategoriaDePeso> query;

        if (q == null || q.isBlank()) {
            query = CategoriaDePeso.findAll(sortObj);
        } else {
            query = CategoriaDePeso.find(
                    "lower(nome) like ?1", sortObj, "%" + q.toLowerCase() + "%");
        }

        long totalElements = query.count();
        int totalPages = query.pageCount();
        List<CategoriaDePeso> categorias = query.page(effectivePage, size).list();

        boolean hasMore = effectivePage < totalPages - 1;
        String nextPage = hasMore ? String.format("/categoriasdepeso/search?q=%s&sort=%s&direction=%s&page=%d&size=%d",
                q != null ? q : "", sort, direction, effectivePage + 1, size) : null;

        var response = new CategoriaDePesoSearchResponse(categorias, totalElements, totalPages, hasMore, nextPage);

        return Response.ok(response).build();
    }

    @POST
    @Idempotent
    @ApiKeyRequired
    @Operation(
            summary = "Adiciona um registro a lista de categorias de peso (insert)",
            description = "Adiciona um item a lista de categorias de peso por meio de POST e request body JSON"
    )
    @RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaDePeso.class)
            )
    )
    @APIResponse(
            responseCode = "201",
            description = "Created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaDePeso.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class))
    )
    @Transactional
    public Response insert(@Valid CategoriaDePeso categoriaDePeso){
        CategoriaDePeso newCategoriaDePeso = new CategoriaDePeso();
        newCategoriaDePeso.nome = categoriaDePeso.nome;
        newCategoriaDePeso.descricao = categoriaDePeso.descricao;
        CategoriaDePeso.persist(newCategoriaDePeso);
        return Response.status(Response.Status.CREATED).entity(newCategoriaDePeso).build();
    }

    @DELETE
    @ApiKeyRequired
    @Operation(
            summary = "Remove um registro da lista de categorias de peso (delete)",
            description = "Remove um item da lista de categorias de peso por meio de Id na URL"
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
            description = "Conflito - Categoria de peso possui lutadores vinculados",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class))
    )
    @Transactional
    @Path("{id}")
    public Response delete(@PathParam("id") long id){
        CategoriaDePeso entity = CategoriaDePeso.findById(id);
        if(entity == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        long lutadoresVinculados = Lutador.count("?1 MEMBER OF categoriasDePeso", entity);
        if(lutadoresVinculados > 0){
            return Response.status(Response.Status.CONFLICT)
                    .entity("Não é possível deletar categoria de peso. Existem " + lutadoresVinculados + " lutador(es) vinculado(s).")
                    .build();
        }

        CategoriaDePeso.deleteById(id);
        return Response.noContent().build();
    }

    @PUT
    @ApiKeyRequired
    @Operation(
            summary = "Altera um registro da lista de categorias de peso (update)",
            description = "Edita um item da lista de categorias de peso por meio de Id na URL e request body JSON"
    )
    @RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaDePeso.class)
            )
    )
    @APIResponse(
            responseCode = "200",
            description = "Item editado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaDePeso.class, type = SchemaType.ARRAY)
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
    public Response update(@PathParam("id") long id,@Valid CategoriaDePeso newCategoriaDePeso){
        CategoriaDePeso entity = CategoriaDePeso.findById(id);
        if(entity == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entity.nome = newCategoriaDePeso.nome;
        entity.descricao = newCategoriaDePeso.descricao;

        return Response.status(Response.Status.OK).entity(entity).build();
    }
}
