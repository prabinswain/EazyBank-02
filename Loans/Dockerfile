FROM eclipse-temurin:21
#Information around who maintains the image
MAINTAINER com.google.com
#Add application's jar to the image
COPY target/loans-0.0.1-SNAPSHOT.jar  loans-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java","-jar","loans-0.0.1-SNAPSHOT.jar"]