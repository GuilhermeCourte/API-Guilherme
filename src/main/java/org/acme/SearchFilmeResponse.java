package org.acme;

import org.acme.dto.PagedResponse;

import java.util.List;

public class SearchFilmeResponse extends PagedResponse<Filme> {
    public SearchFilmeResponse(List<Filme> filmes, long totalFilmes, int totalPages) {
        super(filmes, totalFilmes, totalPages);
    }
}
