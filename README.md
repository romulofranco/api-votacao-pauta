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
Heroku: https://https://api-votacao-startaideia.herokuapp.com/
```

#### Endpoints and API Doc

The documentation should be accessible on:

```
http://localhost:9090/swagger-ui.html#/
```

### Other important settings
In the  **application.yml** you shoud set some settings to provide an environment suitable for such case:
1) Change default time for Vote Session
2) Allow only voter with CPF ABLE_VOTE after request /v1/users/{valid cpf}
3) Allow duplicity for name pauta with already exist
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

