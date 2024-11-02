FROM openjdk:11
EXPOSE 8082
ADD target/*.jar 5ARCTIC3-G6-DevOps.jar
ENTRYPOINT ["java","-jar","/5ARCTIC3-G6-DevOps.jar"]