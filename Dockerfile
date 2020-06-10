FROM java:8
EXPOSE 8080
ADD /target/app-0.0.1-SNAPSHOT.jar app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","app-0.0.1-SNAPSHOT.jar"]
