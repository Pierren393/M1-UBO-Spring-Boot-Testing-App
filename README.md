# 🎬 API Music - Gestion des Utilisateurs et Posters

## Description

API REST pour la gestion des utilisateurs (authentification JWT) et des posters de films.  
Projet réalisé dans le cadre du Master 1 UBO.

## 🛠️ Technologies

- **Backend** : Spring Boot 3, Spring Security, JWT, JPA/Hibernate, MySQL, Swagger/OpenAPI
- **Frontend** : Vue.js 3, Axios, Vue Router
- **Tests** : JUnit 5, Mockito
- **Déploiement** : Docker, Docker Compose

## 🚀 Lancement

### Avec Docker (recommandé)

```bash
docker-compose up --build
```

- Backend : http://localhost:8080
- Frontend : http://localhost:5173
- Swagger UI : http://localhost:8080/swagger-ui.html

### Sans Docker

#### Backend

```bash
cd M1-UBO-Spring-Boot-Testing-App
./gradlew bootRun
```

#### Frontend

```bash
cd frontend
npm install
npm run dev
```

### Scripts

```bash
./start.sh   # Lance le backend et le frontend
./stop.sh    # Arrête les services
```

## 📚 API Endpoints

### Utilisateurs (`/user`)

| Méthode | Endpoint | Description | Auth |
|---------|----------|-------------|------|
| `POST` | `/user` | Créer un utilisateur | ❌ |
| `POST` | `/user/login` | Connexion | ❌ |
| `GET` | `/user` | Lister tous les utilisateurs | 🔒 Admin |
| `GET` | `/user/{pseudo}` | Récupérer un utilisateur | ❌ |
| `PUT` | `/user/{pseudo}` | Modifier un utilisateur | 🔒 Self/Admin |
| `DELETE` | `/user/{pseudo}` | Supprimer un utilisateur | 🔒 Self/Admin |

### Posters (`/poster`)

| Méthode | Endpoint | Description | Auth |
|---------|----------|-------------|------|
| `GET` | `/poster` | Lister tous les posters | ❌ |
| `GET` | `/poster/{id}` | Récupérer un poster | ❌ |
| `POST` | `/poster` | Créer un poster | 🔒 |
| `PUT` | `/poster/{id}` | Modifier un poster | 🔒 |
| `DELETE` | `/poster/{id}` | Supprimer un poster | 🔒 |

## 🔐 Authentification

L'API utilise des **tokens JWT** (Bearer).  
Après inscription (`POST /user`) ou connexion (`POST /user/login`), un token est retourné.  
Ce token doit être inclus dans le header `Authorization: Bearer <token>` pour les endpoints protégés.

## 🧪 Tests

```bash
cd M1-UBO-Spring-Boot-Testing-App
./gradlew test
```

## 📦 Structure du projet

```
API/
├── M1-UBO-Spring-Boot-Testing-App/     # Backend Spring Boot
│   └── src/main/java/com/music/music/dao/
│       ├── config/                      # SecurityConfig, JwtFilter
│       ├── controller/                  # UserController, PosterController
│       ├── dto/                         # DTOs (UserDto, PosterDto, etc.)
│       ├── entity/                      # Entités JPA (User, Poster)
│       ├── mapper/                      # Mappers (UserMapper, PosterMapper)
│       ├── repository/                  # Repositories JPA
│       └── service/                     # Services (interface + impl)
├── frontend/                            # Frontend Vue.js
│   └── src/
│       ├── views/                       # Login, Register, Profile, Posters
│       └── services/                    # API Axios
├── docker-compose.yml                   # Orchestration Docker
├── Dockerfile                           # Image backend
└── README.md
```

## 👥 Données de test

Le fichier `data.sql` peuple automatiquement la base avec :
- 3 utilisateurs (admin/jdupont/mmartin, mot de passe : `password123`)
- 5 posters de films

## 📖 Swagger

Documentation interactive disponible sur : http://localhost:8080/swagger-ui.html

## Auteur

Pierre Nicolas - Master 1 UBO
