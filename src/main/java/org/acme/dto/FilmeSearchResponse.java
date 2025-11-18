package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.acme.Filme;

import java.util.List;

public class FilmeSearchResponse {

    @JsonProperty("Filmes")
    private List<Filme> filmes;

    @JsonProperty("TotalFilmes")
    private long totalFilmes;

    @JsonProperty("TotalPages")
    private int totalPages;

    @JsonProperty("HasMore")
    private boolean hasMore;

    @JsonProperty("NextPage")
    private String nextPage;

    public FilmeSearchResponse(List<Filme> filmes, long totalFilmes, int totalPages, boolean hasMore, String nextPage) {
        this.filmes = filmes;
        this.totalFilmes = totalFilmes;
        this.totalPages = totalPages;
        this.hasMore = hasMore;
        this.nextPage = nextPage;
    }

    // Getters and setters
}
