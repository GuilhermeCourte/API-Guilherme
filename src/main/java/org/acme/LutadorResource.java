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

import org.acme.dto.LutadorSearchResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Path("/lutadores")
public class LutadorResource {

    @GET
    @Operation(
            summary = "Retorna todas as lutas/carreiras (getAll)",
            description = "Retorna uma lista de lutas ou carreiras de boxe no formato JSON"
    )
    @APIResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Lutador.class, type = SchemaType.ARRAY)
            )
    )
    public Response getAll(){
        return Response.ok(Lutador.listAll()).build();
    }

    @GET
    @Path("{id}")
    @Operation(
        summary = "Retorna uma luta/carreira pela busca por ID (getById)",
        description = "Retorna uma luta ou carreira de boxe específica pela busca de ID colocado na URL no formato JSON por padrão"
    )
    @APIResponse(
            responseCode = "200",
            description = "Item retornado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Lutador.class, type = SchemaType.ARRAY)
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
            @Parameter(description = "Id da luta/carreira a ser pesquisada", required = true)
            @PathParam("id") long id){
        Lutador entity = Lutador.findById(id);
        if(entity == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(entity).build();
    }

    @GET
    @Operation(
            summary = "Retorna lutas/carreiras conforme o sistema de pesquisa (search)",
            description = "Retorna uma lista de lutas ou carreiras de boxe filtrada conforme a pesquisa por padrão no formato JSON"
    )
    @APIResponse(
            responseCode = "200",
            description = "Item retornado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LutadorSearchResponse.class)
            )
    )
    @Path("/search")
    public Response search(
            @Parameter(description = "Query de buscar por nome do lutador, ano de nascimento ou número de vitórias")
            @QueryParam("q") String q,
            @Parameter(description = "Campo de ordenação da lista de retorno")
            @QueryParam("sort") @DefaultValue("id") String sort,
            @Parameter(description = "Esquema de filtragem de lutadores por ordem crescente ou decrescente")
            @QueryParam("direction") @DefaultValue("asc") String direction,
            @Parameter(description = "Define qual página será retornada na response")
            @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Define quantos objetos serão retornados por query")
            @QueryParam("size") @DefaultValue("4") int size
    ){
        Set<String> allowed = Set.of("id", "nome", "historia", "anoNascimento", "ranking", "vitorias", "statusCarreira");
        if(!allowed.contains(sort)){
            sort = "id";
        }

        Sort sortObj = Sort.by(
                sort,
                "desc".equalsIgnoreCase(direction) ? Sort.Direction.Descending : Sort.Direction.Ascending
        );

        int effectivePage = Math.max(page, 0);

        PanacheQuery<Lutador> query;

        if (q == null || q.isBlank()) {
            query = Lutador.findAll(sortObj);
        } else {
            try {
                // Tenta converter a pesquisa em número
                int numero = Integer.parseInt(q);

                // Busca apenas em campos numéricos
                query = Lutador.find(
                        "anoNascimento = ?1 or vitorias = ?1",
                        sortObj,
                        numero
                );

            } catch (NumberFormatException e) {
                // se não for número, busca só em campos textuais
                query = Lutador.find(
                        "lower(nome) like ?1",
                        sortObj,
                        "%" + q.toLowerCase() + "%"
                );
            }
        }

        long totalElements = query.count();
        int totalPages = query.pageCount();
        List<Lutador> lutadores = query.page(effectivePage, size).list();

        boolean hasMore = effectivePage < totalPages - 1;
        String nextPage = hasMore ? String.format("/lutadores/search?q=%s&sort=%s&direction=%s&page=%d&size=%d",
                q != null ? q : "", sort, direction, effectivePage + 1, size) : null;

        var response = new LutadorSearchResponse(lutadores, totalElements, totalPages, hasMore, nextPage);

        return Response.ok(response).build();
    }

    @POST
    @Idempotent
    @ApiKeyRequired
    @Operation(
            summary = "Adiciona um registro a lista de lutas/carreiras (insert)",
            description = "Adiciona um item a lista de lutas ou carreiras de boxe por meio de POST e request body JSON"
    )
    @RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Lutador.class)
            )
    )
    @APIResponse(
            responseCode = "201",
            description = "Created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Lutador.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class))
    )
    @Transactional
    public Response insert(@Valid Lutador lutador){
        Lutador newLutador = new Lutador();
        newLutador.nome = lutador.nome;
        newLutador.historia = lutador.historia;
        newLutador.anoNascimento = lutador.anoNascimento;
        newLutador.ranking = lutador.ranking;
        newLutador.vitorias = lutador.vitorias;
        newLutador.statusCarreira = lutador.statusCarreira;
        
        // Resolver treinador (pode ter apenas id)
        if(lutador.treinador != null && lutador.treinador.id != null){
            Treinador t = Treinador.findById(lutador.treinador.id);
            if(t == null){
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Treinador com id " + lutador.treinador.id + " não existe").build();
            }
            newLutador.treinador = t;
        } else {
            newLutador.treinador = null;
        }

        // Resolver categorias de peso (se vierem com id)
        if(lutador.categoriasDePeso != null && !lutador.categoriasDePeso.isEmpty()){
            Set<CategoriaDePeso> resolved = new HashSet<>();
            for(CategoriaDePeso c : lutador.categoriasDePeso){
                if(c == null || c.id == 0){
                    continue;
                }
                CategoriaDePeso fetched = CategoriaDePeso.findById(c.id);
                if(fetched == null){
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Categoria de peso com id " + c.id + " não existe").build();
                }
                resolved.add(fetched);
            }
            newLutador.categoriasDePeso = resolved;
        } else {
            newLutador.categoriasDePeso = new HashSet<>();
        }

        Lutador.persist(newLutador);
        return Response.status(Response.Status.CREATED).entity(newLutador).build();
    }

    @DELETE
    @ApiKeyRequired
    @Operation(
            summary = "Remove um registro da lista de lutas/carreiras (delete)",
            description = "Remove um item da lista de lutas ou carreiras de boxe por meio de Id na URL"
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
    @Transactional
    @Path("{id}")
    public Response delete(@PathParam("id") long id){
        Lutador entity = Lutador.findById(id);
        if(entity == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Remove associações ManyToMany antes de deletar
        entity.categoriasDePeso.clear();
        entity.persist(); // atualiza o estado

        Lutador.deleteById(id);
        return Response.noContent().build();
    }

    @PUT
    @ApiKeyRequired
    @Operation(
            summary = "Altera um registro da lista de lutas/carreiras (update)",
            description = "Edita um item da lista de lutas ou carreiras de boxe por meio de Id na URL e request body JSON"
    )
    @RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Lutador.class)
            )
    )
    @APIResponse(
            responseCode = "200",
            description = "Item editado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Lutador.class, type = SchemaType.ARRAY)
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
    public Response update(@PathParam("id") long id,@Valid Lutador newLutador){
        Lutador entity = Lutador.findById(id);
        if(entity == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entity.nome = newLutador.nome;
        entity.historia = newLutador.historia;
        entity.anoNascimento = newLutador.anoNascimento;
        entity.ranking = newLutador.ranking;
        entity.vitorias = newLutador.vitorias;
        entity.statusCarreira = newLutador.statusCarreira;

        // Resolver treinador
        if(newLutador.treinador != null && newLutador.treinador.id != null){
            Treinador t = Treinador.findById(newLutador.treinador.id);
            if(t == null){
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Treinador com id " + newLutador.treinador.id + " não existe").build();
            }
            entity.treinador = t;
        } else {
            entity.treinador = null;
        }

        // Resolver categorias de peso
        if(newLutador.categoriasDePeso != null){
            Set<CategoriaDePeso> resolved = new HashSet<>();
            for(CategoriaDePeso c : newLutador.categoriasDePeso){
                if(c == null || c.id == 0) continue;
                CategoriaDePeso fetched = CategoriaDePeso.findById(c.id);
                if(fetched == null){
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Categoria de peso com id " + c.id + " não existe").build();
                }
                resolved.add(fetched);
            }
            entity.categoriasDePeso = resolved;
        } else {
            entity.categoriasDePeso = new HashSet<>();
        }

        return Response.status(Response.Status.OK).entity(entity).build();
    }
}
