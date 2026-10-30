# Petstore API Test Automation — REST Assured + Cucumber

BDD-style API tests for the [Swagger Petstore](https://petstore.swagger.io/) API, written during my QA internship (June 2026).

For a newer version of this project with JUnit 5, parameterized tests, negative tests and documented bugs, see [petstore-api-test-automation](https://github.com/ardasdev/petstore-api-test-automation).

## Tech stack

Java 17 · REST Assured 5.5 · Cucumber 7 · Jackson · Lombok · Maven

## What is tested

| Feature | Scenarios |
| --- | --- |
| `pet.feature` | Create, get by ID, update, delete a pet |
| `store.feature` | View inventory, create, get and delete an order |
| `user.feature` | Create a user, log in, get by username, update, delete |

13 scenarios in total, written in Gherkin.

## Design

```
src/test/java/petstore/
├── services/     # BaseTest (RequestSpecification) + one service class per resource
├── steps/        # Cucumber step definitions, call the services
├── models/       # Request/response POJOs (Lombok) and status enums
├── context/      # ScenarioContext: shares data between steps of a scenario
└── runner/       # Cucumber runner
```

- **Service layer:** HTTP calls live in `PetService`, `StoreService` and `UserService`; step definitions only describe the behaviour and assert the results.
- **POJOs with Lombok** for request and response bodies, enums for pet and order status.
- **`RequestSpecBuilder`** keeps the base URI and request/response logging in one place.

## How to run

Run the `TestRunner` class from your IDE. An HTML report is generated in `target/cucumber-html-report`.

## Known limitations

- `ScenarioContext` uses static fields, so scenarios should not be run in parallel. A dependency-injection container (e.g. `cucumber-picocontainer`) would be the next step.
- Petstore is a public, shared demo server; data can change at any time.
