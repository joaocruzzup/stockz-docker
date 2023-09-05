FROM openjdk:11
LABEL maintainer="João Cruz <joao.cruz@zup.com.br>"
COPY target/stockz-0.0.1-SNAPSHOT.jar /app/stockz-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/stockz-0.0.1-SNAPSHOT.jar"]
