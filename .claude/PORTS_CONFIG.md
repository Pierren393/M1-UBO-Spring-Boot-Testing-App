# 🔌 Configuration des Ports - Projet VOD

## Ports configurés (12020-12039)

| Service | Port | URL |
|---------|------|-----|
| **Vue.js Client** | 12020 | http://localhost:12020 |
| **API Gateway** | 12021 | http://localhost:12021 |
| **Film Service** | 12022 | http://localhost:12022 |
| **Evaluation Service** | 12023 | http://localhost:12023 |
| **Poster Service** | 12024 | http://localhost:12024 |
| **Payment Service** | 12025 | http://localhost:12025 |

## Bases de données (ports standards)

| Service | Port | Accès |
|---------|------|-------|
| **MariaDB** | 12026 | localhost:12026 |
| **MongoDB** | 27017 | localhost:27017 |

## 🚀 Démarrage de l'application

### 1. Installer les dépendances Vue.js (première fois)
```powershell
cd vod-client
npm install
cd ..
```

### 2. Démarrer les bases de données
```powershell
docker-compose -f docker-compose-full.yaml up -d mariadb mongodb
```

### 3. Démarrer le Film Service (Terminal 1)
```powershell
.\gradlew bootRun
```
✅ Service disponible sur **http://localhost:12022**

### 4. Démarrer l'API Gateway (Terminal 2)
```powershell
cd api-gateway
..\gradlew bootRun
```
✅ Gateway disponible sur **http://localhost:12021**

### 5. Démarrer le client Vue.js (Terminal 3)
```powershell
cd vod-client
npm run dev
```
✅ Application disponible sur **http://localhost:12020**

## 📱 Accéder à l'application

Ouvrez votre navigateur : **http://localhost:12020**

## 🧪 Tester les endpoints

### Via l'API Gateway (recommandé)
```powershell
# Liste des films
curl http://localhost:12021/api/films

# Liste des artistes
curl http://localhost:12021/api/artists

# Liste des reviews
curl http://localhost:12021/api/reviews
```

### Directement sur le Film Service
```powershell
# Accès direct (sans passer par la gateway)
curl http://localhost:12022/reviews
curl http://localhost:12022/artists
curl http://localhost:12022/dogs
```

## 📊 Architecture réseau

```
┌─────────────────────┐
│   Navigateur Web    │
│  localhost:12020    │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│   Vue.js Client     │
│     Port 12020      │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│   API Gateway       │
│     Port 12021      │  ← Point d'entrée unique
└──────────┬──────────┘
           │
    ┌──────┴──────┬──────────┬──────────┐
    ▼             ▼          ▼          ▼
┌────────┐  ┌─────────┐  ┌───────┐  ┌─────────┐
│ Film   │  │ Eval.   │  │Poster │  │Payment  │
│ 12022  │  │ 12023   │  │12024  │  │ 12025   │
└────┬───┘  └────┬────┘  └───┬───┘  └─────────┘
     │           │            │
     ▼           ▼            ▼
┌─────────┐  ┌──────────┐
│ MariaDB │  │ MongoDB  │
│ 12026   │  │  27017   │
└─────────┘  └──────────┘
```

## ⚙️ Configuration CORS

L'API Gateway autorise les requêtes depuis :
- `http://localhost:12020` (Vue.js)

## 🛑 Arrêter l'application

```powershell
# Dans chaque terminal : Ctrl+C

# Arrêter les bases de données
docker-compose -f docker-compose-full.yaml down
```

## 📝 Notes

- Tous les ports sont dans la plage **12020-12039** comme demandé
- L'API Gateway route toutes les requêtes `/api/*` vers les services backend
- Le client Vue.js est configuré pour appeler l'API Gateway
- Les bases de données utilisent leurs ports standards configurés pour ne pas entrer en conflit
