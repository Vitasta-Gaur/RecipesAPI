package com.abnamro.integration.tests;

import org.junit.jupiter.api.Tag;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ActiveProfiles("integration-test")
@Target( {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Tag("IntegrationTests")
public @interface IntegrationTests {
}