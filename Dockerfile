FROM amazoncorretto:17-alpine-jdk

COPY target/demo-libros-0.0.1-SNAPSHOT.jar /api-v1.jar

ENTRYPOINT ["java", ".jaar", "/api-v1.jar"]