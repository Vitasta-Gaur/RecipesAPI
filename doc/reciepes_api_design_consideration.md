# Reciepe API - Design Decisions

## Status

Proposed

## Context

The objective is to design an API that can serve multiple options to manage the recipes.

## Assumption

* API will be hosted behind the API Gateway to ensure security.

## Decision

* API First Design is Followed. Swagger documentation can be found [here](https://github.com/Vitasta-Gaur/RecipesAPI/blob/2ddb0af504f0d2db0d0d74d68452f4d593ffae41/src/main/resources/swagger/reciepes_api.yaml).
* Postgress DB is considered to manage and work with the data.

## Consequences

* API First ensures that Code and Domain are always in sync.
* API ensures better up-front documentation.