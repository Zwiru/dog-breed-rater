docker build . -t dog-breed-rater:mysql
docker stop dog-breed-rater || true
docker rm dog-breed-rater || true
docker run -d -p 8080:8080 --name=dog-breed-rater --network dbr-network --restart unless-stopped dog-breed-rater:mysql