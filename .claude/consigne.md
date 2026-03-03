# TP API M1 INFORMATIQUE SPRING BOOT

# Introduction

L’enjeu majeur du premier cours est d’installer un environnement de développement stable. En raison des configurations diverses, nous allons devoir nous entraider ! 

<aside>
💡 Afin d’avoir un support personnalisé: 
-Mail [levy.marques.ubo@gmail.com](mailto:levy.marques.ubo@gmail.com) délai de réponse d’une semaine
-Discord `prakkmak` délai de réponse rapide, en plus on peux se faire des partages d’écrans 🎉
-En commentaire sur la page actuelle (je recevrais une notification)

</aside>

# Objectifs des heures

Nous allons tenter de devenir autonome sur l’utilisation du framework Spring Boot. Aujourd’hui il est beaucoup utilisé pour la création de services. Voici les notions à savoir à la fin des heures:

- Utilisation de l’IDE afin de manipuler le framework
- Création des endpoints d’entrée de l’application (Controller)
- Traitements grâce à des services
- Mapping Dto → Entity et Entity → Dto
- Mapping d’entités Hibernate
- Connexion à une base de donnée MySQL locale
- Respect du contrat commum fourni par l’API REST
- Utilisation de Swagger
- Sensibilisation à Gradle

Voici une grille de notation non définitive. 

- Une note sera donnée de manière indicative
- Un feedback sera donné afin d’avoir des axes d’amélioration
- Possibilité de rendre 2 fois dans le temps imparti afin de pouvoir améliorer le code 🙂
- Rendu via un GitLab ou GitHub
- Je te laisse compter le nombre de points total, si tu as bien compris, pas besoin de tout faire 😉 Les premiers items sont les plus importants. Les derniers items permettent d’aller plus loin
- Le tableau n’est pas exhaustif

| **Compétence** | **Coefficient prévisionnel** |
| --- | --- |
| Présentation du projet
-Qualité rendu
-Nommage fichiers | 3 |
| Respect d’un contrat de d’API cohérent | 6 |
| Qualité de code
-Consistance dans le nommage
-Simplicité de code (découper le code en unité fonctionnelle simple)
-... | 5 |
| Quelques tests unitaires (pas besoin de tout tester) | 2 |
| Rendre une collection Postman exhaustive | 2 |
| Utilisation de docker | 2 |
| Avoir la même spécification que l’ensemble du groupe | 2 |
| Création de Mappeurs indépendants (utilisation de librairies ?) et bonne séparation des différentes couches logicielles | 2 |
| Documentation
-Documenter quand le code n’est pas explicite via javadoc ou commentaires
-Readme | 2 |
| Implémentation d’un SWAGGER | 2 |
| Création d’un fichier de peuplement de base de donnée
-Utilisé avec des données cohérentes | 2 |
| Ajouter une nouvelle librairie afin de répondre à un besoin | 2 |
|  | 2 |
| Absence de code mort ☠ | 1 |
| Méthodologie swagger commum | 2 |

# Prérequis

### IDE

Plusieurs solutions s’offrent à nous pour pouvoir travailler sur Spring. Nous allons utiliser un IDE afin de faire fonctionner 

**Solution 1 : IntelliJ**

 IntelliJ (JetBrains) est beaucoup utilisé dans l’industrie mais nécessite une version payante (Pro) afin d’utiliser Spring. e

<aside>
🦸 Cette année, Intellij doit être installé sur les postes. Espérons qu’il n’y a pas de bugs.

</aside>

<aside>
💡 Il est possible de postuler à la version étudiante des logiciels JetBrains grâce à son mail étudiant et de profiter de tous les logiciels en version pro !
[https://www.jetbrains.com/fr-fr/community/education/#students](https://www.jetbrains.com/fr-fr/community/education/#students)

</aside>

**Solution 2 : Eclipse**

Le bon vieux Eclipse à l’avantage d’être open source. Spring a créé sa propre version du logiciel: Spring Tool Suite 4. Dispo en téléchargement ici: [https://spring.io/tools](https://spring.io/tools)

<aside>
💡 Cette solution n’a pas été testé sur les ordinateurs de l’université. Normalement le logiciel est portable et donc exécutable partout.

</aside>

Une manière simple de configurer Eclipse pour Spring est de créer un nouveau projet Spring et d’utiliser le projet git fourni plus loin comme référence.
New > Spring Starter Project > (Remplir) suivant

- Mettre comme starter spring-web, spring-data-jpa, devtools

**Solution 3 : VSCode**

Jamais testé mais peut être intéressant de lancer du Spring sur VSCode. Le plugin est dispo ici: [https://marketplace.visualstudio.com/items?itemName=Pivotal.vscode-boot-dev-pack](https://marketplace.visualstudio.com/items?itemName=Pivotal.vscode-boot-dev-pack)

### JDK

Pour faire fonctionner le projet il serait préférable d’avoir un jdk 11+. Par exemple le JDK 13 est disponible ici [https://jdk.java.net/java-se-ri/13](https://jdk.java.net/java-se-ri/13).

### MySQL

Il y a besoin d’une base de donnée MySQL, tu peux utiliser XAMPP par exemple qui est un assez bon logiciel pour avoir une base rapidement si tu es en local. Idéalement, utilise Docker.

# Exercices

### Exercice 1 - Initialisation du projet

Afin d’utiliser Spring Boot il y a deux choix. Le premier consiste à initialiser le projet soit même. Le deuxième consiste à prendre un projet existant. Il est conseillé de prendre le code de base afin de se simplifier la vie. C’est le code de l’année dernière, espérons qu’il fonctionne encore bien !

```bash
git clone https://github.com/Prakkkmak/M1-UBO-Spring-Boot-Testing-App.git
```

- Cloner ou initialiser le projet Spring Boot
- Initialiser le projet sur son GitLab (ou github) personnel
- Modifier le readme et commit.

### Exercice 2 - Lancement de l’application

- Lancer l’application des deux manières suivantes
    
    <aside>
    ⚠️ Si au lancement il y a des erreurs “jdbc” c’est que l’application n’arrive pas à se connecter à la base de donnée ! le fichier `application.properties` permet de réparer le soucis en y inscrivant des bons identifiants
    
    </aside>
    
    <aside>
    ⚠️ Vérifiez bien que tu aies un JDK dans file... > Project Structure.
    Dans SDK, télécharger un SDK revend.
    
    </aside>
    
    - Lancer le ‘main’ de l’application (Dans App.java)
    - Lancer le ‘bootRun’ des tasks gradle
    

<aside>
💡 Je te conseille de rechercher ce qu’est une task gradle. Spring boot offre de base pleins de tâches pré-écrites.

</aside>

<aside>
💡 Si tu es perdu et que tout semble KO sans raison apparente, utiliser la task ‘clean’ permet de supprimer les fichiers auto générés.

</aside>

<aside>
💡 Pour voir si ton appli est OK (dépendances et compilation) utilise la task ‘assemble’ afin d’assembler le projet.

</aside>

### Exercice 3 - Familiarisation

Tu vas pouvoir te familiariser avec le code. Ne négliges pas cette partie, généralement les projets que tu vas rencontrer seront déjà bien implémenter et il te suffira de t’adapter et “t’inspirer” du code existant.

- Parcourir le code et identifier la chaîne d’appels
    - Où est le point d’entrée ?
    - Quelles sont les URL à appeler pour utiliser l’API ?
    - Identifier le format Controller → Service → Repository

Spring Boot est réputé pour faciliter l’injection de dépendances, un Design Pattern très connu. Historiquement sur Spring il étais nécessaire d’annoter les champs des classes de la manière suivante:

```java
@Service
class MyService {}
```

```java
class Foo1 {
	@Autowired
	private MyService myServiceTruc;
}

class Foo2 {
	@Autowired
	private MyService myServiceMuche;
}
```

Cette structure permet à Spring de créer un singleton de `MyService` et d’affecter cette même instance à tous les champs `@Autowired`. On y injecte une dépendance. Dans l’exemple, `myServiceTruc` est la même instance que `myServiceMuche` . Cela évite de devoir passer à chaque classe utilisant `MyService` une même instance.

<aside>
❓ Mais alors pourquoi il n’y a aucun `@Autowired` dans le code ??

</aside>

Aujourd’hui la bonne pratique est d’utiliser le ‘Constructor Dependecy Injection’. En bref, pour faciliter les tests unitaires notamment, on préfère créer un constructeur avec tous les champs et l’annoter `@Autowired`

```java
class Foo3 {

  //Plus besoin d'annoter @Autowired
	private MyService myServiceMachin;

	@Autowired
	public Foo3(MyService myServiceMachin){
		this.myServiceMachin = myServiceMachin;
	}

}
```

<aside>
❓ Il n’y a toujours aucun `@Autowired` dans le code [.](https://media.makeameme.org/created/dafuq-5cacec.jpg).

</aside>

Lorsqu’il y a un seul constructeur dans la classe l’annotation `@Autowired` est implicite il n’est donc pas nécessaire de l’annoter !

Retenons juste que Spring fais beaucoup de magie derrière et il est nécessaire de creuser afin d’avoir plus d’infos.

- Trouver les injections de dépendance dans le code

### Exercice 4 - Tests Postman

<aside>
🙂 Rappels ‣

</aside>

- Utiliser Postman pour tester les endpoints
- Relancer l’application et vérifier que les données persistent
- Vérifier directement en base l’insertion des données

### Exercice 5 - Création des entités

Tu as bien remarqué que nous n’avions rien à écrire en SQL. Néanmoins nous allons quand même créer les entités nécessaires.

- Créer toutes les entités nécessaires
- Tester en lançant le projet
- Vérifier la création des tables en base

<aside>
💡 Pour les jointures, tu peux te référer à ce tuto : [https://www.baeldung.com/jpa-join-types](https://www.baeldung.com/jpa-join-types)

package com.services;

import com.dtos.AuthentificationDto;

import java.util.List;

/**

- Interface définissant les opérations métier disponibles pour la gestion des chiens.
- Cette interface suit le principe d'Interface Segregation (SOLID).
*/
public interface AuthentificationService {
/*
    - Sauvegarde un chien dans le système
    - @param authentificationDto les données du chien à sauvegarder
    - @return le chien sauvegardé avec son ID généré
    */
    AuthentificationDto saveAuthentification(AuthentificationDto authentificationDto);
    
    /**
    
    - Récupère un chien par son identifiant
    - @param authentificationId l'identifiant du chien recherché
    - @return le chien trouvé
    - @throws jakarta.persistence.EntityNotFoundException si le chien n'existe pas
    */
    AuthentificationDto getAuthentificationById(Long authentificationId);
    
    /**
    
    - Supprime un chien du système
    - @param authentificationId l'identifiant du chien à supprimer
    - @return true si la suppression a réussi
    */
    boolean deleteAuthentification(Long authentificationId);
    
    /**
    
    - Récupère tous les chiens du système
    - @return la liste des chiens
    */
    List<AuthentificationDto> getAllAuthentifications();
    }
</aside>

### Exercice 6 - Création d’un DTO

Les Data Transfert Objects sont des objets avec lesquelles nous travaillons, ils sont souvent similaires aux entités mais peuvent être compléments différents. 

```java
class FormDto {
	private String username;
	private String password;
}
```

Dans cet exemple, le DTO nous sert à stocker les identifiants de connexion. 

- Créer le DTO associé à une entité importante (par exemple Vacataire)
    - Afin de créer le DTO il n’est pas rare de copier coller l’entité associée et de changer le nom de la classe

<aside>
💡 Pour les dates, rajouter dans le Dto une annotation : 
[https://www.baeldung.com/spring-boot-formatting-json-dates](https://www.baeldung.com/spring-boot-formatting-json-dates)

</aside>

- Tester en lançant l’application

<aside>
💡 Dans le projet les DTOs sont nommés “nomDto” et les entités “nom” certains préfèrent faire l’inverse et nommer les DTOs “nom” et les entités “nomEntity”. Le plus important et de garder la même convention du début à la fin du code !

</aside>

### Exercice 7 - Création d’un Repository

Le Repository permet d’effectuer les appels en base simplement. Pour ce projet nous espérons ne pas à avoir à écrire des requêtes SQL. 

- Créer le Repository associé au DTO
- Tester en lançant l’application

<aside>
💡 Le repository est vide car il étends d’une superclasse qui contiens tous pleins de fonctionnalités dont la gestion CRUD de base ! Parfait !

</aside>

### Exercice 8 - Création d’un service

Le service permet d’effectuer toutes les actions métier, l’usage est de créer une interface et son implémentation. L’interface décrit les services que vont rendre le service (lol) et l’implémentation les met en œuvre. 

- Créer le service associé
- Créer le mapping des DTO et Entités

<aside>
💡

Pour mapper il suffit de créer les méthodes de mapping comme dans l’exemple. Généralement nous utilisons un mapper séparé qu’utilise le service.

</aside>

- Tester en lançant l’application

<aside>
💡 C’est le moment ou il faut tester les tests unitaires, nous sommes à l’aveugle sans possibilité de tester la brique fonctionnellement.

</aside>

### Exercice 9 - Création d’un contrôleur

Le contrôleur est le point d’entrée de l’application. 

- Créé le contrôleur associé au service
- Tester en lançant l’application

### Exercice 10 - Tester

- Vérifier que le contrôleur utilise le service
- Vérifier que le service utilise le Repository
- Démarrer l’application 🎉
- Utiliser Postman afin de tester le contrôleur nouvellement créé.

### Exercice 11 - Réitérer

- Finir l’application. Have Fun.

Hoster en local :

- Docker compose pour la stack
- Cloner le projet X
- Docker compose up le projet

Ou hoster le registry sur docker hub 

