package org.acme;

import org.acme.dto.PagedResponse;

import java.util.List;

public class SearchDiretorResponse extends PagedResponse<Diretor> {
    public SearchDiretorResponse(List<Diretor> diretores, long totalDiretores, int totalPages) {
        super(diretores, totalDiretores, totalPages);
    }
}
