# Étape 1 : Utiliser une image Java JDK 17
FROM openjdk:17-jdk-alpine

# Étape 2 : Spécifier l'auteur

# Étape 3 : Créer un répertoire pour l'application
WORKDIR /app

# Étape 4 : Copier le fichier JAR généré dans le conteneur
COPY target/*.jar app.jar

# Étape 5 : Exposer le port sur lequel l'application sera accessible
EXPOSE 8082

# Étape 6 : Définir la commande à exécuter lorsque le conteneur démarre
ENTRYPOINT ["java", "-jar", "app.jar"]
