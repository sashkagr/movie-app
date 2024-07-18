# Movie Application
## Project Description
Description of the business model is in the technical specifications:
[TS](TS)

## Requirements
* The project utilizes Spring Boot, Maven, and PostgreSQL (any database can be connected). Database operations are performed using Hibernate. It is mandatory to create the ```application.yml``` file;
* Java 21
## Installation and Running
* Create file ```pom.xml```
  * Needed to add plugin 
  ```     
            <plugin>
                  <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
  ```
  It is a Maven plugin provided by the Spring Boot framework that simplifies the process of building and packaging Spring Boot applications.
  The purpose of this configuration is to exclude the lombok dependency from the final executable JAR file. This is typically done when the application uses the Lombok library, which provides a set of annotations and tools to reduce boilerplate code, such as getters, setters, and constructors.
  * Needed to add dependencies:
     *  Provides the necessary dependencies to build a web application using Spring MVC.
  ```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
  ```
     *  Enables automatic application restart and other development-time features.
  ```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
        </dependency>
  ```
    * Generates metadata files for IDE auto-completion and validation of custom configuration properties.
  ```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <scope>annotationProcessor</scope>
        </dependency>
  ```
    * Provides a JDBC-based database access layer for the application.
  ```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
  ```
    * Simplifies JDBC-based data access by providing a higher-level abstraction layer.
  ```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jdbc</artifactId>
        </dependency>
  ```
    * Enables the use of the Java Persistence API (JPA) for data access and management.
  ```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
  ```
    * This dependency provides the core functionality of the Hibernate ORM (Object-Relational Mapping) framework, which is used for mapping Java objects to database tables and vice versa.
  ```
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>6.5.2.Final</version>
            <type>pom</type>
        </dependency>
  ```
    * This dependency provides the JDBC (Java Database Connectivity) driver for the PostgreSQL database, allowing the application to interact with a PostgreSQL database.
  ```
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
  ```
    * Lombok is a Java library that provides a set of annotations and tools to reduce boilerplate code, such as getters, setters, and constructors.
  ```
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
  ```
    * ModelMapper is a Java library that simplifies the mapping of objects between different data models, such as converting between DTOs (Data Transfer Objects) and domain entities.
  ```
        <dependency>
            <groupId>org.modelmapper</groupId>
            <artifactId>modelmapper</artifactId>
            <version>2.3.9</version>
        </dependency>
  ```
* Make sure you have connected to the database.
  * Create database in your terminal :
    * ```psql -U yousername```
    * ```CREATE DATABASE name;```
    
  The database consists of two tables: movie and directors, that will be created by java code.
* Configure the ```application.yml``` file as shown in the example. (Note that instead of port ```5432```, write your port.
  )
```
app:
  hibernate:
    url: jdbc:postgresql://localhost:5432/yourdatabase
    username: yourusername
    password: yourpassword
spring:
  autoconfigure:
    exclude:
      - org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
      - org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration

```
* Configure Spring Boot Application in the ```MovieApplication class```.
* Build and Run project;
* It is recommended to install Postman, from which requests will be sent.
  * Postman collection:
    * by URL
    https://documenter.getpostman.com/view/33064693/2sA3kSnhqz
    * by file ```.json```
    [Movie Application.postman_collection.json](Movie%20Application.postman_collection.json)