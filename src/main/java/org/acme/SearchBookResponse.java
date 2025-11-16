package org.acme;

import org.acme.dto.PagedResponse;

import java.util.List;

public class SearchBookResponse extends PagedResponse<Book> {
    public SearchBookResponse(List<Book> books, long totalBooks, int totalPages) {
        super(books, totalBooks, totalPages);
    }
}
