## Github repository readme file reader

Rate limits:
The primary rate limit for unauthenticated requests is 60 requests per hour.
Primary rate limit for authenticated users 5,000 requests per hour.

If you want to send authenticated requests please fill {github.username} and {github.token} properties in application.properties file.

How to build docker image:
`docker build -t github-repo-readme-reader .`
How to start docker container:
`docker run -p 8080:8080 github-repo-readme-reader`




