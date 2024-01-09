FROM openjdk:17-jdk-alpine
LABEL maintainer="popovycholeksii"
COPY target/GithubRepoReadmeReader-0.1.jar github-repo-readme-reader.jar
ENTRYPOINT ["java", "-jar", "github-repo-readme-reader.jar"]
EXPOSE 8080
