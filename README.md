## Github repository readme file reader
Application for reading Read.me files from every user's (for example Spotify) public GitHub repository 
and counting the 3(can be changed) most popular words that are longer than 4 letters.

Rate limits:
The primary rate limit for unauthenticated requests is 60 requests per hour.
Primary rate limit for authenticated users 5,000 requests per hour.

If you want to send authenticated requests please fill {github.username} and {github.token} properties in application.properties file.

How to build docker image:
`docker build -t github-repo-readme-reader .`

How to start docker container:
`docker run -p 8080:8080 github-repo-readme-reader`

Endpoint call example:

REQUEST: GET http://localhost:8080/{repositoryName}?limit=5

RESPONSE:

`[{"word":"https","quantity":373},{"word":"github","quantity":167},{"word":"build","quantity":160}]`







