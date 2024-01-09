FROM openjdk:17-jdk-alpine
LABEL maintainer="popovycholeksii"
COPY target/GithubRepoFileReader-0.1.jar github-repo-file-reader.jar
ENTRYPOINT ["java", "-jar", "github-repo-file-reader.jar"]
EXPOSE 8080
