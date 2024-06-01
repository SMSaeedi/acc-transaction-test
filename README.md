# CapGemeni Service

This is the README file for the account transaction Service application, explaining the environment variables used in the
configuration.

## Environment Variables

### Spring Application Name
- **Variable Name**: `CapgemApplication`
- **Description**: Specifies the name of the Spring Boot application.
- **Default Value**: `CapGemeni-service`

### H2 Configuration
- **Variable Name**: `cap`, `capcap6568`, `http://localhost:9856/h2`
- **Description**: Configures the H2 datasource properties.

### JPA Configuration
- **Variable Name**: `FORMAT_SQL`
- **Description**: Configures JPA and Hibernate properties.
    - `FORMAT_SQL`: Specifies whether to log SQL statements. Default value is `true`.

#### Other Configuration
- **Description**: Additional configurations for JPA and Hibernate.
    - `use_sql_comments`:  Hibernate will generate comments inside the SQL.
    - `hibernate.ddl-auto`: Specifies the automatic schema generation strategy for Hibernate.
    - `defer-datasource-initialization`:  Defining how your database schema should be handled.

## Starting the Application
- **OpenAPI link**: `http://localhost:9856/cap-gem/api.html`

### 1. Setting Environment Variables and Running Main Function in CapgemApplication.java
- Set the required environment
  variables (`SPRING_APPLICATION_NAME`, `H2_USERNAME`, `H2_PASSWORD`, `H2_URL`, `HIBERNATE_SHOW_SQL`)
  according to your environment and requirements.
- Run the `main` function in the `CapgemApplication.java` file to start the Spring Boot application.

### 2. Setting Environment Variables and Running Using Maven
- Set the required environment
  variables (`SPRING_APPLICATION_NAME`, `H2_USERNAME`, `H2_PASSWORD`, `H2_URL`, `HIBERNATE_SHOW_SQL`)
  according to your environment and requirements.
- Open a terminal and navigate to the project directory.
- Run the following Maven command to start the Spring Boot application:
    - `./mvnw spring-boot:run`
    - `mvn spring-boot:run`

## Notes
- Ensure to set the environment variables accordingly before running the CapGemeni Service application.
- Modify the default values of the environment variables as per your environment and requirements.
- Refer to the Spring Boot documentation for more information on configuring applications using environment variables.
- No security is applied in this service.

```bash
docker build $ docker build -t getting-started
docker run $ docker run -dp 127.0.0.1:9854:9854 getting-started
```
