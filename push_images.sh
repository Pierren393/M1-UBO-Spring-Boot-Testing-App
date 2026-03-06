#!/bin/bash

# Configuration
REGISTRY="gitlab-depinfo.univ-brest.fr:5050"
USER="e22002182"
PROJECT="apis"
TAG="v1.0.0"

echo "🚀 Début du processus de build et push vers $REGISTRY..."

# 1. API Gateway
echo "📦 Building API Gateway..."
docker build -t $REGISTRY/$USER/$PROJECT/api-gateway:$TAG -f api-gateway/Dockerfile .
docker push $REGISTRY/$USER/$PROJECT/api-gateway:$TAG

# 2. Authentication API
echo "📦 Building Authentication API..."
docker build -t $REGISTRY/$USER/$PROJECT/authentication-api:$TAG -f authentication-api/Dockerfile .
docker push $REGISTRY/$USER/$PROJECT/authentication-api:$TAG

# 3. Poster API
echo "📦 Building Poster API..."
docker build -t $REGISTRY/$USER/$PROJECT/poster-api:$TAG -f poster-api/Dockerfile .
docker push $REGISTRY/$USER/$PROJECT/poster-api:$TAG

# 4. Frontend (VOD Client)
echo "📦 Building Frontend..."
docker build -t $REGISTRY/$USER/$PROJECT/vod-client:$TAG -f vod-client/Dockerfile ./vod-client
docker push $REGISTRY/$USER/$PROJECT/vod-client:$TAG

echo "✅ Terminé ! Vos images sont en ligne."

# 5. Nettoyage (Optionnel)
# Supprime les anciennes versions d'images devenues orphelines (dangling) pour libérer de l'espace
echo "🧹 Nettoyage des anciennes images locales..."
docker image prune -f
