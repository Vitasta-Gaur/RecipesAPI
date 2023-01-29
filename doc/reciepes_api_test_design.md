# Reciepe API - Design Decisions

## Status

Proposed

## Context

The objective is to define both unit test and integration tests for the API that can be executed seperately.

## Decision

* Surefire plugin is used to execute Unit tests
* Fail Safe Plugin is used to execute Integration Tests
* Seperate Annotations UnitTests and IntegrationTests are defined to tag the tests.
* Spring Boot Test and JUnit5 are used to define the tests.

## Consequences

* Test Seperation is easier.
* Later, if required, a seperate module can be configured as well for IT.
