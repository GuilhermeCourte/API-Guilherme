package org.acme;

import org.acme.dto.PagedResponse;

import java.util.List;

public class SearchGeneroResponse extends PagedResponse<Genero> {
    public SearchGeneroResponse(List<Genero> generos, long totalGeneros, int totalPages) {
        super(generos, totalGeneros, totalPages);
    }
}
