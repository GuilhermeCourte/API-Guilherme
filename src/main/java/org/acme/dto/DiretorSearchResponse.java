package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.acme.Diretor;

import java.util.List;

public class DiretorSearchResponse {

    @JsonProperty("Diretores")
    private List<Diretor> diretores;

    @JsonProperty("TotalDiretores")
    private long totalDiretores;

    @JsonProperty("TotalPages")
    private int totalPages;

    @JsonProperty("HasMore")
    private boolean hasMore;

    @JsonProperty("NextPage")
    private String nextPage;

    public DiretorSearchResponse(List<Diretor> diretores, long totalDiretores, int totalPages, boolean hasMore, String nextPage) {
        this.diretores = diretores;
        this.totalDiretores = totalDiretores;
        this.totalPages = totalPages;
        this.hasMore = hasMore;
        this.nextPage = nextPage;
    }

    // Getters and setters
}
