docker build . -t dog-breed-rater
docker stop dog-breed-rater || true
docker rm dog-breed-rater || true
<<<<<<< Updated upstream
docker run -d -p 8080:8080 --name=dog-breed-rater --network dbr-network --restart unless-stopped dog-breed-rater
=======
docker run -d -p 8080:8080 --name=dog-breed-rater --network dbr-network --restart unless-stopped dog-breed-rater
>>>>>>> Stashed changes
