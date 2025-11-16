package org.acme.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class RateLimitingService {

    public static final int LIMIT = 100; // 100 requests
    private final LoadingCache<String, AtomicInteger> requestCountsPerApiKey;

    public RateLimitingService() {
        // Cache that expires entries 1 minute after the last write access.
        requestCountsPerApiKey = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public AtomicInteger load(String key) {
                        return new AtomicInteger(0);
                    }
                });
    }

    public int getRequestCount(String apiKey) {
        try {
            return requestCountsPerApiKey.get(apiKey).get();
        } catch (ExecutionException e) {
            return 0;
        }
    }

    public int incrementRequestCount(String apiKey) {
        try {
            return requestCountsPerApiKey.get(apiKey).incrementAndGet();
        } catch (ExecutionException e) {
            // Should not happen
            return 0;
        }
    }
}
