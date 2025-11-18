package org.acme.security;

import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import jakarta.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@SecurityRequirement(name = "apiKey")
public @interface ApiKeyRequired {
}
