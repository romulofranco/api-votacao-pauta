# API de Votação de Pautas

Esta API tem por finalidade permitir criar pautas, abrir uma sessão para votação com tempo determinado para encerramento e consequentemente permite realizar a votação de pautas por meio do CPF de cada votante.

## Documentação de API 

- [Swagger Documentation API](https://sistema-votacao-de-pauta.herokuapp.com/swagger-ui.html#/pauta-controller)

## Plataforma 

- Java 8;
- Spring Boot Framework;
- Heroku Cloud;

## Baixando e executando o projeto

Com Java 8, Maven e Heroku CLI instalado, siga as instruções:[Heroku CLI](https://cli.heroku.com/).

```sh
$ git clone https://github.com/heroku/sistema-votacao-de-pauta.git
$ cd sistema-votacao-de-pauta
$ mvn install
$ heroku local:start
```

Seu app heroku será executado localmente em [localhost:5000](http://localhost:5000/).

Se deseja seguir com banco de dados, use `.env` arquivo de configuração:

```
JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/java_database_name
```

## Fazendo deploy no Heroku

```sh
$ heroku create
$ git push heroku main
$ heroku open
```

## Postman collection test

Caminho: /postman/postman_collection_to_test_requests.json
