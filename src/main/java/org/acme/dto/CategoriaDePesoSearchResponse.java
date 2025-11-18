package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.acme.CategoriaDePeso;

import java.util.List;

public class CategoriaDePesoSearchResponse {

    @JsonProperty("CategoriasDePeso")
    private List<CategoriaDePeso> categoriasDePeso;

    @JsonProperty("TotalCategoriasDePeso")
    private long totalCategoriasDePeso;

    @JsonProperty("TotalPages")
    private int totalPages;

    @JsonProperty("HasMore")
    private boolean hasMore;

    @JsonProperty("NextPage")
    private String nextPage;

    public CategoriaDePesoSearchResponse(List<CategoriaDePeso> categoriasDePeso, long totalCategoriasDePeso, int totalPages, boolean hasMore, String nextPage) {
        this.categoriasDePeso = categoriasDePeso;
        this.totalCategoriasDePeso = totalCategoriasDePeso;
        this.totalPages = totalPages;
        this.hasMore = hasMore;
        this.nextPage = nextPage;
    }

    // Getters and setters
}
