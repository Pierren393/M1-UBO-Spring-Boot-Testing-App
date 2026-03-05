#!/bin/bash
echo "🚀 Génération des images Docker..."

echo "1. Compilation des applications Spring Boot (Gateway, Auth, Poster)"
sudo ./gradlew bootBuildImage

echo "2. Compilation de l'interface Vue.js (vod-client)"
cd vod-client
sudo docker build -t gitlab-depinfo.univ-brest.fr:5050/e22002182/apis/vod-client:latest .
cd ..

echo "✅ Terminé ! Vous pouvez maintenant lancer : sudo docker-compose -f docker-compose-local.yaml up -d"
