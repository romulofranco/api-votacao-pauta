# API Votacao Pauta Sample BackEnd
This is an API  REST API Sample by using Spring Boot API to vote for some topics

#### Prerequisites
- [OpenJDK 8]
- [Docker 19.03.12] (Optional)
- [Maven 3.6+]

#### Running

At prompt level, type commands below:

```
git clone https://github.com/romulofranco/api-votacao-pauta.git
cd api-votacao-pauta
```

You can run the application by using maven directly or by using a Docker container

Maven:

```
mvn clean compile spring-boot:run
```

By using docker, first build an image

```
docker build -t springio/api-votacao . 
```

Run an image that you've created

```
docker run -p 9090:8080 springio/api-votacao
```

### Get access to it

In the both case (Maven or Docker), you should get access to app using:

```
http://localhost:9090/
```

### Demo

A demo can be accessed in:

```
Heroku: https://api-votacao-startaideia.herokuapp.com/
```

#### Endpoints and API Doc

The documentation should be accessible on:

```
http://localhost:9090/swagger-ui.html#/
```
### Database engine

The application uses H2 SQL Driver for the default data persistence engine, so in that case when in runtime this creates a file in the same path of the running application and uses the name votacaodb. Even when the application is restarted the data will remain. To change this setting for a sophisticated database engine, it is necessary to change application.yml and when it's running on Heroku the PostgresSQL can be activated by changing these lines:
```
spring:
datasource:
--> driverClassName: 'org.postgresql.Driver'
    maxActive: '20'
    maxIdle: '15'
    minIdle: '2'
    initialSize: '10'
    removeAbandoned: false
    sql-script-encoding: UTF-8
--> url: ${JDBC_DATABASE_URL:} # to use on heroku deployment
--> #url: 'jdbc:h2:file:~/votacaodb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE'
--> #username: sa
```
### Other important settings
In the  **application.yml** you should set some settings to provide an environment suitable for such case:
1) Change default time for Vote Session
2) Allow only voter with CPF ABLE_TO_VOTE after request /v1/users/{valid cpf}
3) Allow duplicity for name pauta that already exist
```
 votacao:
  tempo:
    sessao:
      minutos: '30'
  eleitor:
    persistir:
      permissao:
        voto: true
  pauta:
    permitir:
      duplicidade:
        nome: true
```

