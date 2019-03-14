#login
#docker login docker.topica.vn -u username -p password

#build inheritance
docker build -f Dockerfile-inheritance -t docker.topica.vn/icovn-prometheus-alert-webhook:1.0.4-SNAPSHOT .

#push
docker push docker.topica.vn/icovn-prometheus-alert-webhook:1.0.4-SNAPSHOT
