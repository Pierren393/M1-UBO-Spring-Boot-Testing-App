# 🚀 Lancer le projet API

## Prérequis
- Java 17+ (installé : Java 21)
- Node.js 18+ (installé : Node 20)
- Être sur le réseau UBO (ou VPN) pour accéder à la base MariaDB obiwan

## 1. Lancer le Backend (Spring Boot - port 8080)

```bash
cd /home2/etudiants/master1/pierre.nicolas/IdeaProjects/API/M1-UBO-Spring-Boot-Testing-App
./gradlew bootRun
```

## 2. Lancer le Frontend (Vue.js - port 5173)

Dans un **autre terminal** :

```bash
cd /home2/etudiants/master1/pierre.nicolas/IdeaProjects/API/frontend
npm install
npm run dev
```

## 3. Accéder à l'application

| Service         | URL                                  |
|-----------------|--------------------------------------|
| Frontend Vue.js | http://localhost:5173                 |
| Backend API     | http://localhost:8080                 |
| Swagger UI      | http://localhost:8080/swagger-ui.html |

> ⚠️ Si tu accèdes depuis un autre PC, remplace `localhost` par le hostname de la machine (ex: `22hp063`).

## 4. Tout lancer d'un coup

```bash
cd /home2/etudiants/master1/pierre.nicolas/IdeaProjects/API
./start.sh
```

## 5. Tout arrêter

```bash
cd /home2/etudiants/master1/pierre.nicolas/IdeaProjects/API
./stop.sh
```
