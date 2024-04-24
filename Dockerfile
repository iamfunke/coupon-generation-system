FROM maven:3-sapmachine-11 as build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests=true

FROM amazoncorretto:11.0.23-alpine as production
WORKDIR /app
COPY --from=build /app/target/coupon-generation-system.jar /app/coupon-generation-system.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/coupon-generation-system.jar"]