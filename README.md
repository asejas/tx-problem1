### StudentClasses
This project implements a REST API that solve the problem statement, and was used by the author to learn a lot about Spring Boot technologies.

## Problem Statement
Create a REST API for a system that assigns students to classes.  API can be used by both a UI and programmatically by other systems.

## Technologies/Frameworks
As the problem needs to be solved with a REST service, Spring Boot was an easy choice.
The project was implemented as a multi-layered application:
- Controllers: Define the endpoints.
- Services: Implements the business logic (works with DTO in order to handle data).  
- JPA Repository: In order to deal with JPA for the persistence.
- Domain: Domain classes.

Code to deal with exception management, swagger integration, configuration, and dto mapping (this was required specially to deal with some issues related to a JPA circular reference) was also required, and the integration tests.  
 
So the main technologies used are:
* Spring Boot for the REST services implementation.
* JPA and Spring-Data for persistence.
* projectlombok in order to avoid some boiler plate code.
* h2database as an in-memory database for the persistence (used in the integrations tests also).
* springfox-swagger2 for swagger integration.


## Deliverables  

* Java code (this repository).
* a short write-up (this, in the previous section).
* deployable/runnable war/jar. This can be generated with a maven command:
```mvn clean package``` and can be executed with this command: ```java -jar {generatedJarFile}```.
* (optional) API documentation. Can be generated with this command: ```mvn javadoc:javadoc```. The application has also swagger integrated, once the application is running, swagger can be reviewed at: ```http://localhost:8080/swagger-ui.html```
