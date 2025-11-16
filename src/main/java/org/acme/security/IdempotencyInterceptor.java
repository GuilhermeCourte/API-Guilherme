package org.acme.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import org.acme.IdempotencyRecord;

import java.util.Optional;

@Idempotent
@Interceptor
public class IdempotencyInterceptor {

    private static final String IDEMPOTENCY_KEY_HEADER = "Idempotency-Key";

    @Inject
    IdempotencyService idempotencyService;

    @Inject
    ObjectMapper objectMapper;

    @AroundInvoke
    public Object checkIdempotency(InvocationContext context) throws Exception {
        HttpHeaders headers = JaxRsContextHolder.getHeaders();
        if (headers == null) {
            // Should not happen if the ContextFilter is configured correctly
            return context.proceed();
        }
        String idempotencyKey = headers.getHeaderString(IDEMPOTENCY_KEY_HEADER);

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            return context.proceed();
        }

        Optional<IdempotencyRecord> recordOpt = idempotencyService.find(idempotencyKey);

        if (recordOpt.isPresent()) {
            IdempotencyRecord record = recordOpt.get();
            Object storedBody = (record.responseBody != null) ? objectMapper.readValue(record.responseBody, Object.class) : null;
            return Response.status(record.responseStatus).entity(storedBody).build();
        }

        Object result = context.proceed();

        if (result instanceof Response) {
            Response response = (Response) result;
            // Only cache successful responses
            if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                idempotencyService.createRecord(idempotencyKey, response);
            }
        }

        return result;
    }
}
