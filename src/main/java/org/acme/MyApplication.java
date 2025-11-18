package org.acme;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/")
@OpenAPIDefinition(
    info = @Info(
        title = "Boxing API",
        version = "1.0.0",
        description = "An API for managing boxers, coaches, and weight classes."
    )
)
@SecurityScheme(
    securitySchemeName = "apiKey",
    type = SecuritySchemeType.APIKEY,
    in = org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn.HEADER,
    apiKeyName = "X-API-KEY"
)
public class MyApplication extends Application {
}
