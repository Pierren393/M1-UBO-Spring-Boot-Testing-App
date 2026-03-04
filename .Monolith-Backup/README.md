# Déployer l'API Authentication

## Prérequis

- Projet GitLab créé : https://gitlab-depinfo.univ-brest.fr/e22002182/apis

## Commandes de déploiement

### 1. Se connecter au registry GitLab
```bash
docker login gitlab-depinfo.univ-brest.fr:5050
```

### 2. Générer l'image Docker
```bash
cd M1-UBO-Spring-Boot-Testing-App
./gradlew bootBuildImage
```

### 3. Pousser l'image sur le registry
```bash
docker image push gitlab-depinfo.univ-brest.fr:5050/e22002182/apis/authentication-api:v1.0.0
```

### 4. Sur le serveur info-tpsi
```bash
ssh e22002182@info-tpsi.univ-brest.fr
docker login gitlab-depinfo.univ-brest.fr:5050
docker-compose up -d
```

## Ports utilisés
- **12020** : API Authentication (Spring Boot)
- **12021** : Base de données MySQL

