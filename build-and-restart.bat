docker build . -t dog-breed-rater
docker stop dog-breed-rater || true
docker rm dog-breed-rater || true
docker run -d -p 8080:8080 --name=dog-breed-rater dog-breed-rater
