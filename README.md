# Backend: Timers Service (Spring Boot)

This service provides a timer platform with template-driven scheduling, instance materialization, and a REST API (Swagger/OpenAPI). It uses Spring Boot, Spring Data JPA, Quartz, and OpenAPI codegen.

## Stack
- Java 17, Spring Boot (Web, Validation, Data JPA)
- Quartz Scheduler (in-memory for dev)
- H2 in-memory DB for dev
- springdoc-openapi + openapi-generator
- Gradle Wrapper

## Run
- Build: `./gradlew build`
- Run: `./gradlew bootRun`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- H2 Console: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:timers`)

## Project layout
```
backend/
  openapi/api.yaml                 # API contract (single source of truth)
  build/generated/...              # OpenAPI-generated code (added to sources)
  src/main/java/com/example/timers
    domain/
      TimerStatus.java
      template/TimerTemplate.java  # Template with condition arrays
      instance/TimerInstance.java  # Materialized combination (one per set of conditions)
      execution/...                # TimerExecution + enums
    repository/                    # Spring Data JPA repositories
    scheduler/
      TimerFireJob.java            # One-success-per-day guard, updates lastTriggeredAt
      TimerScheduler.java          # Schedules/updates Quartz jobs
    service/
      TemplateMaterializer.java    # Expands template→instances, saves, schedules
      TemplateService.java         # Create/update + materialize
    web/
      TemplatesApiDelegateImpl.java
      InstancesApiDelegateImpl.java (stubs for now)
  src/main/resources/application.properties
```

## Domain model
- Template (`timer_templates`)
  - id, name, description, cronExpression, zoneId (IANA), triggerTime, suspended
  - conditions: countries[], regions[], subregions[], flowTypes[], clientIds[], productTypes[] (stored via `@ElementCollection`)
- Instance (`timer_instances`)
  - id (deterministic hash of templateId+dimensions), templateId, country/region/subregion/flowType/clientId/productType
  - status (ACTIVE/SUSPENDED), zoneId, lastSuccessAt, lastAttemptAt, attemptCountToday
- Execution (`timer_executions`)
  - instanceId, scheduledFor, startedAt, finishedAt, outcome (SUCCESS/FAILED/SKIPPED), errorMessage, triggerType (SCHEDULED/MANUAL), idempotencyKey

## Scheduling model
- One Quartz job per Instance; cron evaluated in `zoneId` with misfire policy DoNothing.
- `TimerFireJob` enforces one successful execution per calendar day per instance (in its zone) and updates `lastSuccessAt`.
- **Persistent storage**: Jobs stored in database (H2) via Quartz JDBC store for reliability across restarts.
- **Job lifecycle management**: Quartz jobs are automatically paused/resumed when instances are suspended/resumed.
- **Human-readable job descriptions**: Quartz jobs include descriptive names showing template, dimensions, and schedule for easier debugging and monitoring.
- Templates create/update:
  - `TemplateService.createAndMaterialize` → `TemplateMaterializer.materializeAndSchedule`:
    - Compute cartesian product of conditions
    - Upsert instances via `TimerInstanceRepository.saveAll`
    - Schedule each via `TimerScheduler.scheduleOrUpdate(cron, zone)`

## API (see `openapi/api.yaml`)
- Templates
  - `GET /api/templates` — list
  - `POST /api/templates` — create template and materialize instances
  - `GET /api/templates/{id}` — get
  - `PUT /api/templates/{id}` — update template and re-materialize
- Instances
  - `GET /api/instances` — list/filter (implementation stubs; add filtering in an `InstanceService`)
  - `POST /api/instances/_suspend|_resume|_trigger` — batch ops (stubs)
- Instances
  - `GET /api/instances` — list/filter instances
  - `POST /api/instances/_suspend|_resume|_trigger` — batch operations

## Code generation
- Generate: `./gradlew openApiGenerate`
- Generated sources are added to the main source set at `build/generated/src/main/java`.

## Configuration (dev)
- `application.properties` configures H2, JPA `ddl-auto=update`, formatted SQL, and H2 console.

## Tests
- `TemplateMaterializerTest` — cartesian materialization + scheduling calls
- Tests for template materialization and instance management
- `DemoApplicationSmokeTest` — Spring context loads
- Run tests: `./gradlew test`

## Next steps
- Implement `InstanceService` for filtering + batch suspend/resume/trigger (and wire into `InstancesApiDelegateImpl`).
- Add template-level overlay suspend (optional): effective active = `!template.suspended && instance.status==ACTIVE`.
- Persist executions and expose history endpoints.
- Switch to PostgreSQL + Flyway and Quartz JDBC store for production.


