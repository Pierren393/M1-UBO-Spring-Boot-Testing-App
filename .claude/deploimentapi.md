Bonjour

Voici ci-dessous les instructions pour utiliser le serveur dédié au projet pour déployer vos API. Partout où il est indiqué e12345678, il s'agit de votre login ENT à utiliser.

Sur la feuille partagée des groupes, vous avez pour chaque groupe une plage de 20 ports utilisables sur le serveur. Chaque API doit utiliser un port propre et n'utilisez que ceux de votre plage pour ne pas interférer avec ceux des autres groupes.

Une fois que vous avez une API fonctionnelle et déployée, remplacez le X dans le tableau par le numéro de port utilisé par votre API. Ainsi, d'autres groupes pourront utiliser votre API.

Rappel du lien vers la feuille partagée (à ouvrir via votre compte Google associé à votre compte UBO) : https://docs.google.com/spreadsheets/d/15nYgl91Im7Z2XPpzsZoxmD4yBc3nYyusEce4vOlA43w/edit?gid=0#gid=0

Cordialement

Eric Cariou



------
Le serveur est fonctionnel. Il s'agit d'un simple serveur Linux sur lequel les étudiants peuvent se connecter et exécuter des conteneurs docker.

Pour la connexion, ça se fait par SSH avec le login/mdp ENT : ssh e12345678@info-tpsi.univ-brest.fr.

Ci-joint un fichier docker-compose pour exécuter les 3 API demandées + la BDD de chaque API.

Il faut d'abord générer les images de conteneur à partir des applis java et les pousser sur le docker hub ou sur le registry gitlab du département avant de pouvoir lancer la stack compose.

Pour les applis Spring, il y a une tâche gradle qui permet directement la génération, et elles peuvent être configurées directement dans docker compose pour l'accès à la BDD (voir le fichier joint).

Pour les applis servlet "simples", il faut générer l'archive WAR et l'embarquer dans un conteneur tomcat (voir le Dockerfile en pièce-jointe).
Dans le cas des applis servlet, il faut veiller à modifier les réglages de persistence dans application.properties pour utiliser l'adresse du conteneur mysql.

Les applis peuvent continuer à utiliser obiwan comme serveur mongo, il reste accessible par les conteneurs docker.

Une fois les images construites, il faut les pousser sur le docker hub ou sur notre registry.

Pour notre registry : c'est l'instance gitlab du département, il faut y créer un projet (pas besoin de mettre de code, on réserve juste un namespace).

Avec un projet https://gitlab-depinfo.univ-brest.fr/e12345678/apis, les images doivent par exemple être taggées :

    - gitlab-depinfo.univ-brest.fr:5050/e12345678/apis/student-api:v1.0.0

    - gitlab-depinfo.univ-brest.fr:5050/e12345678/apis/authentication-api:v1.0.0

    - gitlab-depinfo.univ-brest.fr:5050/e12345678/apis/message-api:v1.0.0

Voici les commandes :

docker image tag monimagelocale:montag gitlab-depinfo.univ-brest.fr:5050/e12345678/apis/student-api:v1.0.0 # pour tagger
puis : docker image push gitlab-depinfo.univ-brest.fr:5050/e12345678/apis/student-api:v1.0.0 # pour mettre en ligne sur le registry
Une fois tout cela fait, il est possible d'utiliser le fichier docker compose pour lancer la stack applicative.


Code Java des Servlet (si vous en avez)
Code Spring
partie client : pages HTML avec Vue.js

