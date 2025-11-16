package org.acme.security;

import jakarta.ws.rs.core.HttpHeaders;

public class JaxRsContextHolder {

    private static final ThreadLocal<HttpHeaders> HTTP_HEADERS = new ThreadLocal<>();

    public static void setHeaders(HttpHeaders headers) {
        HTTP_HEADERS.set(headers);
    }

    public static HttpHeaders getHeaders() {
        return HTTP_HEADERS.get();
    }

    public static void clear() {
        HTTP_HEADERS.remove();
    }
}
