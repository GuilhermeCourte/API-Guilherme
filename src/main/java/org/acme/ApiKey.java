package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class ApiKey extends PanacheEntityBase {

    @Id
    public String apiKey;

    public String description;

    public LocalDate creationDate;

    public static ApiKey generate(String description) {
        ApiKey apiKey = new ApiKey();
        apiKey.apiKey = UUID.randomUUID().toString();
        apiKey.description = description;
        apiKey.creationDate = LocalDate.now();
        return apiKey;
    }
}
