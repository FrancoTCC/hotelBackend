FROM maven AS build

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk

ARG JAR_FILE=target/hotelBacked-0.0.1-SNAPSHOT.jar
COPY --from=build /app/${JAR_FILE} app_hotelBacked.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app_hotelBacked.jar"]
