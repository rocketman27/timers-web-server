# Backend: Timers Service (Spring Boot)

This service provides a timer platform with timer-driven scheduling (no instances), Kafka event publication, and a REST API (Swagger/OpenAPI). It uses Spring Boot, Spring Data JPA, Quartz (JDBC JobStore), and OpenAPI codegen.

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
      timer/Timer.java             # Timer with filter arrays and schedule
      execution/...                # TimerExecution + enums
    repository/                    # Spring Data JPA repositories
    scheduler/
      TimerFireJob.java            # Publishes event for each firing, records execution
      TimerScheduler.java          # Schedules/updates Quartz jobs by timerId
    service/
      TimerService.java            # Create/update + schedule timers
    web/
      TemplatesApiDelegateImpl.java  # Manages Timers via existing Template API shapes
      InstancesApiDelegateImpl.java  # Returns 410 Gone (deprecated)
  src/main/resources/application.properties
```

## Domain model
- Timer (`timers`)
  - id, name, description, cronExpression, zoneId (IANA), triggerTime, suspended
  - filters: countries[], regions[], subregions[], flowTypes[], clientIds[], productTypes[] (stored via `@ElementCollection`)
- Execution (`timer_executions`)
  - timerId, scheduledFor, startedAt, finishedAt, outcome (SUCCESS/FAILED/SKIPPED), errorMessage, triggerType (SCHEDULED/MANUAL), idempotencyKey

## Scheduling model
- One Quartz job per Timer. The job key is `(name=timerId, group=instances)`. The trigger key matches the job key.
- Schedule is resolved from `cronExpression` if provided; otherwise derived daily from `triggerTime`. All cron evaluation respects the timer `zoneId`.
- Misfires use DoNothing to avoid catch-up floods after downtime.
- `TimerFireJob` executes on fire: publishes a TimerExecutionEvent to Kafka and records a `TimerExecution` row with `triggerType` MANUAL or SCHEDULED.
- Manual trigger (`POST /api/timers/{id}/_trigger`) creates a one-time immediate trigger and does not persist a new cron trigger (it appears transiently in `QRTZ_FIRED_TRIGGERS`).
- **Persistent storage**: Quartz jobs and triggers stored in DB (H2 in dev) via Quartz JDBC store for reliability.

## API (see `openapi/api.yaml`)
- Timers (legacy tag name "Templates" in the spec)
  - `GET /api/templates` — list timers
  - `POST /api/templates` — create timer and schedule (resolves cron/trigger time)
  - `GET /api/templates/{id}` — get timer by id
  - `PUT /api/templates/{id}` — update timer and reschedule; if `suspended=true`, the job is paused (Quartz state `PAUSED`), otherwise `WAITING`.
- Triggers
  - `POST /api/timers/{id}/_trigger` — manual, immediate fire (records execution with `TRIGGER_TYPE=MANUAL`)
  - `POST /api/timers/_trigger` — batch manual trigger by ids
- Executions
  - `GET /api/executions` — list execution records (basic pagination)

## Code generation
- Generate: `./gradlew openApiGenerate`
- Generated sources are added to the main source set at `build/generated/src/main/java`.

## Configuration (dev)
- `application.properties` configures H2, JPA `ddl-auto=update`, Quartz JDBC JobStore (`spring.quartz.job-store-type=jdbc`), and H2 console.
- Kafka properties exist; when enabled, `KafkaEventService` publishes to topic `app.kafka.topic.timer-executions` (default `timer-executions`).

## Tests
- Integration tests cover: create/update, suspend/resume, manual/scheduled triggers, multi-timer scenarios, Quartz state (`WAITING`/`PAUSED`), cron updates (`QRTZ_CRON_TRIGGERS`), collection persistence, and validation (zone/cron).
- Embedded Kafka tests are scaffolded and currently disabled; enable when publishing is desired.
- Run tests: `./gradlew test`

## Logic overview (high-level)
- Create/update timer → `TimerService.saveAndSchedule` persists `Timer` and schedules Quartz job via `TimerScheduler`.
- If `suspended=true`, the job is paused right after scheduling; otherwise, it remains active (`WAITING`).
- On fire (scheduled or manual), `TimerFireJob` loads the `Timer`, executes placeholder business logic, publishes a Kafka event (if enabled), and records a `TimerExecution` with `outcome` and `triggerType`.

## Next steps
- Expose execution history filters by `timerId`.
- Switch to PostgreSQL + Flyway for DDL and Quartz JDBC store in production.


