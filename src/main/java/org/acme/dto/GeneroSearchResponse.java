package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.acme.Genero;

import java.util.List;

public class GeneroSearchResponse {

    @JsonProperty("Generos")
    private List<Genero> generos;

    @JsonProperty("TotalGeneros")
    private long totalGeneros;

    @JsonProperty("TotalPages")
    private int totalPages;

    @JsonProperty("HasMore")
    private boolean hasMore;

    @JsonProperty("NextPage")
    private String nextPage;

    public GeneroSearchResponse(List<Genero> generos, long totalGeneros, int totalPages, boolean hasMore, String nextPage) {
        this.generos = generos;
        this.totalGeneros = totalGeneros;
        this.totalPages = totalPages;
        this.hasMore = hasMore;
        this.nextPage = nextPage;
    }

    // Getters and setters
}
