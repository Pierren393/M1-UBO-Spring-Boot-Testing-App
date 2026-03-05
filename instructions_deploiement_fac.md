# 🚀 Guide de Déploiement UBO (À faire à la Fac)

Comme le VPN bloque l'envoi des grosses images Docker vers le registre `gitlab-depinfo.univ-brest.fr:5050`, vous allez suivre cette procédure extrêmement simple une fois que vous serez **physiquement à la Fac** (ou sur le Wi-Fi Eduroam).

Le projet de code est maintenant **100% prêt et configuré pour le serveur**. Vous n'avez plus rien à modifier dans le code.

---

## Étape 1 : Tout reconstruire (Optionnel mais recommandé)
Ouvrez un terminal dans votre projet `M1-UBO-Spring-Boot-Testing-App` (sur votre ordinateur portable) et regénérez les images Docker locales toutes propres :
```bash
./build-all.sh
```

## Étape 2 : Envoyer les images sur GitLab UBO
Toujours sur votre PC à la Fac, connectez-vous au registre (avec votre login `e22002182` et votre mot de passe ENT) :
```bash
docker login gitlab-depinfo.univ-brest.fr:5050 -u e22002182
```
⚠️ *Note: Si `docker login` échoue, essayez sans `sudo` selon la configuration de votre Docker.*

Puis poussez les 4 images :
```bash
docker push gitlab-depinfo.univ-brest.fr:5050/e22002182/apis/api-gateway:v1.0.0
docker push gitlab-depinfo.univ-brest.fr:5050/e22002182/apis/authentication-api:v1.0.0
docker push gitlab-depinfo.univ-brest.fr:5050/e22002182/apis/poster-api:v1.0.0
docker push gitlab-depinfo.univ-brest.fr:5050/e22002182/apis/vod-client:latest
```

## Étape 3 : Envoyer le fichier de configuration sur le serveur
Où que vous soyez, ce fichier est vital pour le serveur. Copiez-le via SCP vers le serveur de run :
```bash
scp -r docker-compose.yaml e22002182@info-tpsi.univ-brest.fr:~/
```

## Étape 4 : Lancer l'application sur le serveur UBO !
Connectez-vous intimement au serveur de production :
```bash
ssh e22002182@info-tpsi.univ-brest.fr
```

Une fois sur ce serveur distant, connectez-le à GitLab pour qu'il ait le droit de télécharger vos images :
```bash
docker login gitlab-depinfo.univ-brest.fr:5050 -u e22002182
```

Et lancez enfin l'application !
```bash
docker compose up -d
```

---
### 🛠️ En cas de problème (Le Plan B depuis la maison)
Si même à la fac cela ne fonctionne pas, utilisez le "chemin sans port 5050 depuis le PC" :
1. Envoyez tout le code sur le serveur : `scp -r . e22002182@info-tpsi.univ-brest.fr:~/projet-api/`
2. Allez sur le serveur : `ssh e22002182@info-tpsi.univ-brest.fr`
3. Allez dans le dossier : `cd projet-api`
4. Buildez tout directement là-bas : `./build-all.sh`
5. Lancez tout de suite : `docker compose up -d`
