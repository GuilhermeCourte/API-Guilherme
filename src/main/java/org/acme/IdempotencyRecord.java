package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class IdempotencyRecord extends PanacheEntityBase {

    @Id
    public String idempotencyKey;

    public int responseStatus;

    @Column(length = 2048) // To store a reasonable amount of response data
    public String responseBody;

    public LocalDateTime creationDate;

    public static void create(String key, int status, String body) {
        IdempotencyRecord record = new IdempotencyRecord();
        record.idempotencyKey = key;
        record.responseStatus = status;
        record.responseBody = body;
        record.creationDate = LocalDateTime.now();
        record.persist();
    }
}
