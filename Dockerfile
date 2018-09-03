# install maven, jdk
FROM maven:3.5-jdk-8-alpine AS build-env

# create creditapp-service directory under /app
WORKDIR /app
RUN mkdir -p creditapp-service

# copy all files recursively to /app/creditapp-service directory
COPY . /app/creditapp-service/

# run mvn
RUN mvn clean package

EXPOSE 443

ENTRYPOINT ["java", "-jar", "/app/creditapp-service/target/creditapp-service.jar"]



