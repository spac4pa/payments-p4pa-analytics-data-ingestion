# p4pa-analytics-data-ingestion

This application represent the data ingestion layer of the analytics tool related to **Piattaforma Unitaria** product (PU).

See [PU Microservice Architecture](https://pagopa.atlassian.net/wiki/spaces/SPAC/pages/1405845916/Architettura+microservizi) for more details.

See [p4pa-doc](https://github.com/pagopa/p4pa-doc) for further documentation.

## 🧱 Role

* To ingest the data filling `raw` layer.

## 🌐 APIs
See [OpenAPI](openapi/generated.openapi.json), exposed through the following path:
* `/swagger-ui/index.html`

### 📌 Relevant APIs
* `GET /workflows/{workflowId}/status`: To get workflow status;
* `GET /schedules/{scheduleId}/info`: To get schedule info;
* `POST /workflow/debt-position-type-orgs-ingestion`: To start DebtPositionTypeOrgsIngestion WF.

### 📌 Common HTTP status returned:
* `401`: Invalid access token provided, thus a new login is required;
* `403`: Trying to access a not authorized resource.

## 🌐 AsyncAPIs
See [AsyncAPI](asyncapi/generated.asyncapi.json), exposed through the following path:
* `/springwolf/asyncapi-ui.html`

## 🔎 Monitoring
See available actuator endpoints through the following path:
* `/actuator`

### 📌 Relevant endpoints
* Health (provide an accessToken to see details): `/actuator/health`
  * Liveness: `/actuator/health/liveness`
  * Readiness: `/actuator/health/readiness`
* Metrics: `/actuator/metrics`
  * Prometheus: `/actuator/prometheus`

Further endpoints are exposed through the JMX console.

## ✏️ Logging
See [log configured pattern](/src/main/resources/logback-spring.xml).

## 🔗 Dependencies

### 🗄️ Resources
* Postgresql
* Temporal.io
* Kafka

### 🧩 Microservices
* [p4pa-auth](https://github.com/pagopa/p4pa-auth):
  * To obtain a technical access token (used on WF to call inner microservices);
* [p4pa-debt-positions](https://github.com/pagopa/p4pa-debt-positions):
  * To obtain a DebtPositionTypeOrg data.

## 🔧 Configuration

See [application.yml](src/main/resources/application.yml) for each configurable property.

### 📌 Relevant configurations

#### 🌐 Application Server
| ENV         | DESCRIPTION                       | DEFAULT |
|-------------|-----------------------------------|---------|
| SERVER_PORT | Application server listening port | 8080    |

#### ✏️ Logging
| ENV                                      | DESCRIPTION                                                                                                                                               | DEFAULT |
|------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|---------|
| LOG_LEVEL_ROOT                           | Base level                                                                                                                                                | INFO    |
| LOG_LEVEL_PAGOPA                         | Base level of custom classes                                                                                                                              | INFO    |
| LOG_LEVEL_SPRING                         | Level applied to Spring framework                                                                                                                         | INFO    |
| LOG_LEVEL_SPRING_BOOT_AVAILABILITY       | To print availability events                                                                                                                              | DEBUG   |
| LOGGING_LEVEL_API_REQUEST_EXCEPTION      | Level applied to APIs exception                                                                                                                           | INFO    |
| LOG_LEVEL_PERFORMANCE_LOG                | Level applied to [PerformanceLog](https://raw.githubusercontent.com/pagopa/p4pa-doc/refs/heads/main/reference/technical-docs/Logging.pdf)                 | INFO    |
| LOG_LEVEL_PERFORMANCE_LOG_API_REQUEST    | Level applied to [API Performance Log](https://raw.githubusercontent.com/pagopa/p4pa-doc/refs/heads/main/reference/technical-docs/Logging.pdf)            | INFO    |
| LOG_LEVEL_PERFORMANCE_LOG_REST_INVOKE    | Level applied to [REST invoke Performance Log](https://raw.githubusercontent.com/pagopa/p4pa-doc/refs/heads/main/reference/technical-docs/Logging.pdf)    | INFO    |
| LOG_LEVEL_PERFORMANCE_LOG_INCOMING_EVENT | Level applied to [Incoming event Performance Log](https://raw.githubusercontent.com/pagopa/p4pa-doc/refs/heads/main/reference/technical-docs/Logging.pdf) | INFO    |

#### 🔁 Integrations

##### 🗄️ Resources
| ENV                   | DESCRIPTION                                                                   | DEFAULT                                                                                                                      |
|-----------------------|-------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|
| SHOW_SQL              | To print SQL statements                                                       | false                                                                                                                        |
| ANALYTICS_DB_URL      | PostgreSQL connection string (to use in order to customize the entire string) | jdbc:postgresql://${CLASSIFICATION_DB_HOST}:${CLASSIFICATION_DB_PORT}/${CLASSIFICATION_DB_NAME}?currentSchema=debt_positions |
| ANALYTICS_DB_HOST     | PostgreSQL Host                                                               | localhost                                                                                                                    |
| ANALYTICS_DB_PORT     | PostgreSQL port                                                               | 5432                                                                                                                         |
| ANALYTICS_DB_NAME     | PostgreSQL Database name                                                      | payhub                                                                                                                       |
| ANALYTICS_DB_USER     | PostgreSQL username                                                           |                                                                                                                              |
| ANALYTICS_DB_PASSWORD | PostgreSQL password                                                           |                                                                                                                              |

##### 🔗 REST
| ENV                                               | DESCRIPTION                               | DEFAULT |
|---------------------------------------------------|-------------------------------------------|---------|
| DEFAULT_REST_CONNECTION_POOL_SIZE                 | Default connection pool size              | 10      |
| DEFAULT_REST_CONNECTION_POOL_SIZE_PER_ROUTE       | Default connection pool size per route    | 5       |
| DEFAULT_REST_CONNECTION_POOL_TIME_TO_LIVE_MINUTES | Default connection pool TTL (minutes)     | 10      |
| DEFAULT_REST_TIMEOUT_CONNECT_MILLIS               | Default connection timeout (milliseconds) | 120000  |
| DEFAULT_REST_TIMEOUT_READ_MILLIS                  | Default read timeout (milliseconds)       | 120000  |

##### 🧩 Microservices
| ENV                                  | DESCRIPTION                                     | DEFAULT |
|--------------------------------------|-------------------------------------------------|---------|
| AUTH_BASE_URL                        | Auth microservice URL                           |         |
| AUTH_MAX_ATTEMPTS                    | Auth API max attempts                           | 3       |
| AUTH_WAIT_TIME_MILLIS                | Auth retry waiting time (milliseconds)          | 500     |
| AUTH_PRINT_BODY_WHEN_ERROR           | To print body when an error occurs              | true    |
| DEBT_POSITIONS_BASE_URL              | DebtPositions microservice URL                  |         |
| DEBT_POSITIONS_MAX_ATTEMPTS          | DebtPositions API max attempts                  | 3       |
| DEBT_POSITIONS_WAIT_TIME_MILLIS      | DebtPositions retry waiting time (milliseconds) | 500     |
| DEBT_POSITIONS_PRINT_BODY_WHEN_ERROR | To print body when an error occurs              | true    |

##### 🌀 KAFKA
| ENV                                | DESCRIPTION                                                        | DEFAULT                |
|------------------------------------|--------------------------------------------------------------------|------------------------|
| KAFKA_BINDER_BROKER                | Comma separated list of brokers to which the Kafka binder connects |                        |
| KAFKA_PAYMENTS_BINDER_BROKER       | Comma separated list of brokers to which the Kafka binder connects | ${KAFKA_BINDER_BROKER} |
| KAFKA_DATA_EVENTS_BINDER_BROKER    | Comma separated list of brokers to which the Kafka binder connects | ${KAFKA_BINDER_BROKER} |
| KAFKA_AUDIT_BINDER_BROKER          | Comma separated list of brokers to which the Kafka binder connects | ${KAFKA_BINDER_BROKER} |
| KAFKA_CONFIG_HEARTBEAT_INTERVAL_MS | Hearth beat interval (milliseconds)                                | 3000                   |
| KAFKA_CONFIG_SESSION_TIMEOUT_MS    | Session timeout (milliseconds)                                     | 30000                  |
| KAFKA_CONFIG_REQUEST_TIMEOUT_MS    | Request timeout (milliseconds)                                     | 60000                  |
| KAFKA_CONFIG_METADATA_MAX_AGE      | Metadata max age (milliseconds)                                    | 180000                 |
| KAFKA_CONFIG_SASL_MECHANISM        | SASL mechanism                                                     | PLAIN                  |
| KAFKA_CONFIG_SECURITY_PROTOCOL     | Security protocol                                                  | SASL_SSL               |
| KAFKA_CONFIG_MAX_REQUEST_SIZE      | Max request size                                                   | 1000000                |

###### 📥 KAFKA CONSUMERS
| ENV                                                 | DESCRIPTION                                                                                    | DEFAULT                                            |
|-----------------------------------------------------|------------------------------------------------------------------------------------------------|----------------------------------------------------|
| KAFKA_CONSUMER_CONFIG_AUTO_COMMIT                   | True if the acknowledgement of the message is implicit if there are not errors                 | true                                               |
| KAFKA_CONSUMER_CONFIG_CONNECTIONS_MAX_IDLE_MS       | Maximum lifetime for idle connections (milliseconds)                                           | 180000                                             |
| KAFKA_CONFIG_MAX_POLL_INTERVAL_TIMEOUT_MS           | Maximum interval between polls declared toward the broker (milliseconds)                       | 300000                                             |
| KAFKA_CONSUMER_CONFIG_MAX_POLL_SIZE                 | Maximum number of messages fetch for each poll                                                 | 500                                                |
| KAFKA_CONSUMER_CONFIG_CONNECTION_TIMEOUT_MS         | Initial timeout configured for the connection process (milliseconds)                           | 100000                                             |
| KAFKA_CONSUMER_CONFIG_CONNECTION_TIMEOUT_MAX_MS     | Maximum timeout configured when connection attempts repeatedly fail (milliseconds)             | 200000                                             |
| KAFKA_CONSUMER_CONFIG_STANDARD_HEADERS              | If ask for contextual metadata headers when reading messages                                   | both                                               |
| KAFKA_CONSUMER_CONFIG_START_OFFSET                  | Where the consumer should begins consuming messages from a topic's partition (earliest/latest) | earliest                                           |
| KAFKA_TOPIC_PAYMENTS                                | Topic where to read payment event                                                              | p4pa-payhub-payments-evh                           |
| KAFKA_PAYMENTS_CONSUMER_SASL_JAAS_CONFIG            | JAAS Config string used to perform authentication                                              |                                                    |
| KAFKA_PAYMENTS_GROUP_ID                             | Consumer group id                                                                              | p4pa-workflow-hub-consumer-group                   |
| KAFKA_PAYMENTS_CONSUMER_ENABLED                     | If the consumer should read messages                                                           | true                                               |
| KAFKA_PAYMENTS_AUTO_COMMIT                          | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_AUTO_COMMIT}               |
| KAFKA_PAYMENTS_REQUEST_CONNECTIONS_MAX_IDLE_MS      | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_CONNECTIONS_MAX_IDLE_MS}   |
| KAFKA_PAYMENTS_INTERVAL_TIMEOUT_MS                  | See default config description                                                                 | ${KAFKA_CONFIG_MAX_POLL_INTERVAL_TIMEOUT_MS}       |
| KAFKA_PAYMENTS_MAX_POLL_SIZE                        | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_MAX_POLL_SIZE}             |
| KAFKA_PAYMENTS_REQUEST_CONNECTION_TIMEOUT_MAX_MS    | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_CONNECTION_TIMEOUT_MAX_MS} |
| KAFKA_PAYMENTS_REQUEST_CONNECTION_TIMEOUT_MS        | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_CONNECTION_TIMEOUT_MS}     |
| KAFKA_PAYMENTS_STANDARD_HEADERS                     | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_STANDARD_HEADERS}          |
| KAFKA_PAYMENTS_REQUEST_START_OFFSET                 | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_START_OFFSET}              |
| KAFKA_TOPIC_DATA_EVENTS                             | Topic where to read payment event                                                              | p4pa-payhub-payments-evh                           |
| KAFKA_DATA_EVENTS_CONSUMER_SASL_JAAS_CONFIG         | JAAS Config string used to perform authentication                                              |                                                    |
| KAFKA_DATA_EVENTS_GROUP_ID                          | Consumer group id                                                                              | p4pa-workflow-hub-consumer-group                   |
| KAFKA_DATA_EVENTS_CONSUMER_ENABLED                  | If the consumer should read messages                                                           | true                                               |
| KAFKA_DATA_EVENTS_AUTO_COMMIT                       | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_AUTO_COMMIT}               |
| KAFKA_DATA_EVENTS_REQUEST_CONNECTIONS_MAX_IDLE_MS   | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_CONNECTIONS_MAX_IDLE_MS}   |
| KAFKA_DATA_EVENTS_INTERVAL_TIMEOUT_MS               | See default config description                                                                 | ${KAFKA_CONFIG_MAX_POLL_INTERVAL_TIMEOUT_MS}       |
| KAFKA_DATA_EVENTS_MAX_POLL_SIZE                     | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_MAX_POLL_SIZE}             |
| KAFKA_DATA_EVENTS_REQUEST_CONNECTION_TIMEOUT_MAX_MS | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_CONNECTION_TIMEOUT_MAX_MS} |
| KAFKA_DATA_EVENTS_REQUEST_CONNECTION_TIMEOUT_MS     | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_CONNECTION_TIMEOUT_MS}     |
| KAFKA_DATA_EVENTS_STANDARD_HEADERS                  | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_STANDARD_HEADERS}          |
| KAFKA_DATA_EVENTS_REQUEST_START_OFFSET              | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_START_OFFSET}              |
| KAFKA_TOPIC_AUDIT                                   | Topic where to read payment event                                                              | p4pa-payhub-payments-evh                           |
| KAFKA_AUDIT_CONSUMER_SASL_JAAS_CONFIG               | JAAS Config string used to perform authentication                                              |                                                    |
| KAFKA_AUDIT_GROUP_ID                                | Consumer group id                                                                              | p4pa-workflow-hub-consumer-group                   |
| KAFKA_AUDIT_CONSUMER_ENABLED                        | If the consumer should read messages                                                           | true                                               |
| KAFKA_AUDIT_AUTO_COMMIT                             | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_AUTO_COMMIT}               |
| KAFKA_AUDIT_REQUEST_CONNECTIONS_MAX_IDLE_MS         | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_CONNECTIONS_MAX_IDLE_MS}   |
| KAFKA_AUDIT_INTERVAL_TIMEOUT_MS                     | See default config description                                                                 | ${KAFKA_CONFIG_MAX_POLL_INTERVAL_TIMEOUT_MS}       |
| KAFKA_AUDIT_MAX_POLL_SIZE                           | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_MAX_POLL_SIZE}             |
| KAFKA_AUDIT_REQUEST_CONNECTION_TIMEOUT_MAX_MS       | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_CONNECTION_TIMEOUT_MAX_MS} |
| KAFKA_AUDIT_REQUEST_CONNECTION_TIMEOUT_MS           | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_CONNECTION_TIMEOUT_MS}     |
| KAFKA_AUDIT_STANDARD_HEADERS                        | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_STANDARD_HEADERS}          |
| KAFKA_AUDIT_REQUEST_START_OFFSET                    | See default config description                                                                 | ${KAFKA_CONSUMER_CONFIG_START_OFFSET}              |

##### 🕒 Temporal.io
| ENV                                                       | DESCRIPTION                                                            | DEFAULT      |
|-----------------------------------------------------------|------------------------------------------------------------------------|--------------|
| TEMPORAL_SERVER_HOST                                      | Temporal hostname                                                      | localhost    |
| TEMPORAL_SERVER_PORT                                      | Temporal port                                                          | 7233         |
| TEMPORAL_SERVER_ENABLE_HTTPS                              | To use HTTPS when invoking Temporal                                    | false        |
| TEMPORAL_SERVER_NAMESPACE                                 | Temporal namespace                                                     | pu-analytics |
| TEMPORAL_TIMEOUT_SYSTEM_INFO_SECONDS                      | Timeout set to wait for SystemInfo invokes (seconds)                   | 5            |
| TEMPORAL_TIMEOUT_RPC_LONG_POLL_SECONDS                    | Timeout set to wait for long poll RPCs (seconds)                       | 70           |
| TEMPORAL_TIMEOUT_RPC_QUERY_SECONDS                        | Timeout set to wait for query RPCs (seconds)                           | 10           |
| TEMPORAL_TIMEOUT_RPC_GENERIC_SECONDS                      | Timeout set to wait for other RPCs (seconds)                           | 10           |
| DEFAULT_ACTIVITY_CONFIG_START_TO_CLOSE_TIMEOUT_IN_SECONDS | Default startToClose activity timeout (seconds)                        | 300          |
| DEFAULT_ACTIVITY_CONFIG_RETRY_INITIAL_INTERVAL_IN_MILLIS  | Default initial interval to wait during retries (milliseconds)         | 1000         |
| DEFAULT_ACTIVITY_CONFIG_RETRY_BACKOFF_COEFFICIENT         | Default backoff coefficient used to increase the delay between retries | 1.5          |
| DEFAULT_ACTIVITY_CONFIG_RETRY_MAXIMUM_ATTEMPTS            | Default maximum number of retries                                      | 30           |

See `workflow.*` properties on [application.yml](src/main/resources/application.yml) to check configuration for each workflow.

###### 📥 TaskQueue poller sizes
See the following properties for poller sizes:
* `spring.temporal.workers[*].capacity.workflow-task-pollers-configuration.poller-behavior-autoscaling`
* `spring.temporal.workers[*].capacity.activity-task-pollers-configuration.poller-behavior-autoscaling`

#### 💼 Business logic
| ENV                                  | DESCRIPTION                                                 | DEFAULT    |
|--------------------------------------|-------------------------------------------------------------|------------|
| SCHEDULE_DP_TYPE_ORGS_INGESTION_CRON | Frequency of DPTypeOrgs data ingestion WF (cron expression) | 0 1 * * *  |

#### 🔑 keys
| ENV                  | DESCRIPTION                                                                              | DEFAULT |
|----------------------|------------------------------------------------------------------------------------------|---------|
| JWT_TOKEN_PUBLIC_KEY | p4pa-auth JWT public key                                                                 |         |
| AUTH_CLIENT_SECRET   | client_secret used on M2M authentication to get a technical access token                 |         |

## 🛠️ Getting Started

### 📝 Prerequisites

Ensure the following tools are installed on your machine:

1. **Java 21+**
2. **Gradle** (or use the Gradle wrapper included in the repository)
3. **Docker** (to build and run on an isolated environment, optional)

### 🔐 Write Locks

```sh
./gradlew dependencies --write-locks
```

### ⚙️ Build

```sh
./gradlew clean build
```

### 🧪 Test

#### 📌 JUnit
```sh
./gradlew test
```

### 🚀 Run local

```sh
./gradlew bootRun
```

### 🐳 Build & run through Docker
```sh
docker build -t <APP_NAME> .
docker run --env-file <ENV_FILE> <APP_NAME>
```

### ⚖️ Generate dependencies licenses
```sh
./gradlew generateLicenseReport
```
