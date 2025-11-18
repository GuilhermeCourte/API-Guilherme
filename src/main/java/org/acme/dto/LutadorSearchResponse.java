package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.acme.Lutador;

import java.util.List;

public class LutadorSearchResponse {

    @JsonProperty("Lutadores")
    private List<Lutador> lutadores;

    @JsonProperty("TotalLutadores")
    private long totalLutadores;

    @JsonProperty("TotalPages")
    private int totalPages;

    @JsonProperty("HasMore")
    private boolean hasMore;

    @JsonProperty("NextPage")
    private String nextPage;

    public LutadorSearchResponse(List<Lutador> lutadores, long totalLutadores, int totalPages, boolean hasMore, String nextPage) {
        this.lutadores = lutadores;
        this.totalLutadores = totalLutadores;
        this.totalPages = totalPages;
        this.hasMore = hasMore;
        this.nextPage = nextPage;
    }

    // Getters and setters
}
