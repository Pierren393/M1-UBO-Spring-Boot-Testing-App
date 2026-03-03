#!/bin/bash
echo "🚀 Lancement du projet API..."

# Lancer le backend Spring Boot
echo "▶️  Démarrage du backend (port 8080)..."
cd /home2/etudiants/master1/pierre.nicolas/IdeaProjects/API/M1-UBO-Spring-Boot-Testing-App
./gradlew bootRun &> /tmp/backend.log &
echo $! > /tmp/backend.pid
echo "   PID backend: $(cat /tmp/backend.pid)"

# Lancer le frontend Vue.js
echo "▶️  Démarrage du frontend (port 5173)..."
cd /home2/etudiants/master1/pierre.nicolas/IdeaProjects/API/frontend
npm run dev &> /tmp/frontend.log &
echo $! > /tmp/frontend.pid
echo "   PID frontend: $(cat /tmp/frontend.pid)"

echo ""
echo "✅ Les deux serveurs démarrent en arrière-plan."
echo "   Backend  → http://localhost:8080"
echo "   Frontend → http://localhost:5173"
echo "   Swagger  → http://localhost:8080/swagger-ui.html"
echo ""
echo "📋 Logs: tail -f /tmp/backend.log /tmp/frontend.log"
echo "🛑 Arrêter: ./stop.sh"
