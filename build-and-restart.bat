docker build . -t dog-breed-rater
docker stop dog-breed-rater || true
docker rm dog-breed-rater || true
docker run -d -p 8080:8080 --name=ogloszex --network dbr-network --restart unless-stopped dog-breed-rater

@REM docker run -d -p 8080:8080 --name=ogloszex -e SPRING_PROFILES_ACTIVE=prod --network dbr-network --restart unless-stopped dog-breed-rater