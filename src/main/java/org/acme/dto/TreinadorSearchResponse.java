package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.acme.Treinador;

import java.util.List;

public class TreinadorSearchResponse {

    @JsonProperty("Treinadores")
    private List<Treinador> treinadores;

    @JsonProperty("TotalTreinadores")
    private long totalTreinadores;

    @JsonProperty("TotalPages")
    private int totalPages;

    @JsonProperty("HasMore")
    private boolean hasMore;

    @JsonProperty("NextPage")
    private String nextPage;

    public TreinadorSearchResponse(List<Treinador> treinadores, long totalTreinadores, int totalPages, boolean hasMore, String nextPage) {
        this.treinadores = treinadores;
        this.totalTreinadores = totalTreinadores;
        this.totalPages = totalPages;
        this.hasMore = hasMore;
        this.nextPage = nextPage;
    }

    // Getters and setters
}
