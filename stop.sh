#!/bin/bash
echo "🛑 Arrêt du projet API..."

# Arrêter le backend
if [ -f /tmp/backend.pid ]; then
    kill $(cat /tmp/backend.pid) 2>/dev/null
    echo "   Backend arrêté."
    rm /tmp/backend.pid
fi

# Arrêter le frontend
if [ -f /tmp/frontend.pid ]; then
    kill $(cat /tmp/frontend.pid) 2>/dev/null
    echo "   Frontend arrêté."
    rm /tmp/frontend.pid
fi

# Kill tout ce qui écoute sur 8080 et 5173
lsof -ti:8080 | xargs kill -9 2>/dev/null
lsof -ti:5173 | xargs kill -9 2>/dev/null

echo "✅ Tous les serveurs sont arrêtés."
