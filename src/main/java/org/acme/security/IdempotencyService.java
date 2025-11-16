package org.acme.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.acme.IdempotencyRecord;

import java.util.Optional;

@ApplicationScoped
public class IdempotencyService {

    @Inject
    ObjectMapper objectMapper;

    @Transactional
    public Optional<IdempotencyRecord> find(String key) {
        return IdempotencyRecord.findByIdOptional(key);
    }

    @Transactional
    public void createRecord(String key, Response response) {
        try {
            String body = response.hasEntity() ? objectMapper.writeValueAsString(response.getEntity()) : null;
            IdempotencyRecord.create(key, response.getStatus(), body);
        } catch (JsonProcessingException e) {
            // Handle serialization error, maybe log it
            e.printStackTrace();
        }
    }
}
