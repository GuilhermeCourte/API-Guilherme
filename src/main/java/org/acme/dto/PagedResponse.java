package org.acme.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagedResponse<T> {
    public List<T> data;
    public long totalElements;
    public int totalPages;
    public Map<String, String> links = new HashMap<>();

    public PagedResponse(List<T> data, long totalElements, int totalPages) {
        this.data = data;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
