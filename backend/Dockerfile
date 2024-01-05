FROM maven:3.9.6-amazoncorretto-21
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests
RUN mvn package -DskipTests

ENTRYPOINT ["java", "-jar", "target/wave-remote-demo-0.0.1-SNAPSHOT.jar"]
