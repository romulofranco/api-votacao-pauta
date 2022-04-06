FROM maven:3.5.2-jdk-8-alpine AS build
COPY pom.xml /tmp/
RUN mvn -B dependency:go-offline -f /tmp/pom.xml -s /usr/share/maven/ref/settings-docker.xml
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml verify

FROM java:8-jre-alpine
WORKDIR /app
EXPOSE 9090
COPY --from=build /tmp/target/*.jar /app/api-votacao.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/ecommerce.jar"]