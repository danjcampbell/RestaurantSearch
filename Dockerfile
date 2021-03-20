FROM openjdk:11

ADD target/restaurantsearch-0.0.1-SNAPSHOT.jar restaurantsearch.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","restaurantsearch.jar"]