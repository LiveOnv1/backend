FROM amazoncorretto:17

WORKDIR /app

COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

RUN chmod +x gradlew

RUN ./gradlew build -x test

COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
