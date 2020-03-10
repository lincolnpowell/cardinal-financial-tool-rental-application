FROM maven:3.6.3 AS build
LABEL stage=intermediate
COPY pom.xml .
ADD /src ./src
RUN mvn clean package; \
    mv /target/tool-rental-application.jar .; \
    rm -rf /target

FROM openjdk:8u242
LABEL maintainer="org.cardinalfinancial"
ENV TZ="America/New_York"
COPY --from=build ./tool-rental-application.jar .
ENTRYPOINT java -jar ./tool-rental-application.jar