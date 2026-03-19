# start with a base image containing java runtime
FROM eclipse-temurin:21
#Information around who maintains the image
MAINTAINER com.google.com
#Add application's jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar  accounts-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]