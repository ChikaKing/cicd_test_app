FROM openjdk:17
EXPOSE 8087
ADD target/cicd-test-app.jar cicd-test-app.jar
ENTRYPOINT ["java", "-jar", "/cicd-test-app.jar"]
