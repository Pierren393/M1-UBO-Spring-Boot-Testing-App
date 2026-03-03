# Lancer le projet sous IntelliJ IDEA

## Prérequis

- **IntelliJ IDEA** (version Pro recommandée pour le support Spring)
- **JDK 17+** (configurable dans File > Project Structure > SDK)
- **MariaDB** en local sur le port 3306 (via XAMPP ou Docker)

## Étapes

### 1. Ouvrir le projet

- **File > Open** → sélectionner le dossier racine du projet
- IntelliJ détecte automatiquement le `build.gradle` et importe les dépendances

### 2. Configurer la base de données

Vérifier les identifiants dans `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/e22002182_db1
spring.datasource.username=e22002182sql
spring.datasource.password=20ok2UVf
```

> ⚠️ La base `e22002182_db1` doit exister sur le serveur MariaDB.

### 3. Lancer l'application

**Option A — Via le main :**
- Ouvrir `src/main/java/com/App.java`
- Cliquer sur le ▶️ vert à côté de `public static void main`

**Option B — Via Gradle :**
- Ouvrir le panneau **Gradle** (à droite)
- Tasks > application > **bootRun**
- Double-cliquer pour lancer

### 4. Vérifier que ça fonctionne

- L'application démarre sur `http://localhost:8080`
- **Swagger UI** : [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **API Docs** : [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

## En cas de problème

| Problème | Solution |
|----------|----------|
| Erreur JDBC / connexion BDD | Vérifier que MariaDB tourne et que les identifiants sont corrects dans `application.properties` |
| Pas de JDK détecté | File > Project Structure > SDK → télécharger un JDK 17+ |
| Erreurs de compilation bizarres | Gradle > Tasks > build > **clean**, puis relancer |
| Lombok non reconnu | Activer l'annotation processing : Settings > Build > Compiler > Annotation Processors > ✅ Enable |
