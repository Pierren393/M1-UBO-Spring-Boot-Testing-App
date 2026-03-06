# 🎬 API VOD - Gestion des Utilisateurs et Posters

## Description

Système distribué en microservices pour la gestion des utilisateurs (authentification JWT) et des posters de films.  
Projet réalisé dans le cadre du Master 1 UBO.

## 🛠️ Architecture Technologique

- **API Gateway** : Spring Cloud Gateway (Port 12021)
- **Authentication API** : Spring Boot + MariaDB (Port 12022)
- **Poster API** : Spring Boot + MongoDB (Port 12024)
- **Frontend** : Vue.js 3 (Port 12020)
- **Bases de données** : 
  - MariaDB : Hébergée sur `obiwan.univ-brest.fr`
  - MongoDB : Conteneur local

---

## 🚀 Lancement sur les PC de la Faculté (Sans VPN)

Sur les PC de l'université, vous êtes déjà sur le réseau interne. Utilisez le fichier compose standard.

1. **Cloner le projet et se placer sur la branche `docker-local`** :
   ```bash
   git clone <url-du-repo>
   cd M1-UBO-Spring-Boot-Testing-App
   git checkout docker-local
   ```

2. **Lancer l'application avec Docker Compose** :
   ```bash
   docker compose up --build
   ```

---

## 🏠 Lancement en Local (Avec VPN)

Si vous travaillez depuis chez vous avec le VPN de l'université, un conflit d'adresse IP Docker peut survenir. Utilisez la configuration dédiée :

```bash
docker compose -f docker-compose-local.yaml up --build
```

---

## 🔗 Accès aux services

Une fois l'application lancée, les services sont accessibles via la Gateway :

- **Frontend** : [http://localhost:12020](http://localhost:12020)
- **Swagger (Auth)** : [http://localhost:12021/swagger-ui/index.html?configUrl=/v3/api-docs/user/swagger-config](http://localhost:12021/swagger-ui/index.html?configUrl=/v3/api-docs/user/swagger-config)
- **Swagger (Poster)** : [http://localhost:12021/swagger-ui/index.html?configUrl=/v3/api-docs/poster/swagger-config](http://localhost:12021/swagger-ui/index.html?configUrl=/v3/api-docs/poster/swagger-config)

---

## 🧪 Tests

Pour lancer la suite de tests unitaires et d'intégration :

```bash
# Tests Auth API
cd authentication-api && ./gradlew test

# Tests Poster API
cd ../poster-api && ./gradlew test
```

## 📦 Structure du projet

```
.
├── api-gateway/          # Routage et point d'entrée unique
├── authentication-api/   # Gestion des utilisateurs (MariaDB)
├── poster-api/           # Gestion des posters (MongoDB)
├── vod-client/           # Interface utilisateur (Vue.js)
├── docker-compose.yaml   # Config pour la Fac
└── docker-compose-local.yaml # Config pour le Local (VPN)
```

## 👥 Données de test (SQL)

Le système initialise automatiquement :
- **admin@test.com** / `password123` (Admin)
- **jdupont@test.com** / `password123` (User)

---
Pierre Nicolas - Master 1 UBO
