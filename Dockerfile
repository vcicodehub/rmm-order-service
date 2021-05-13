# Dockerfile - Repair Harmonization POC
# RH Services

FROM adoptopenjdk/openjdk11
ARG JAR_FILE=build/libs/*.jar
EXPOSE 80
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]