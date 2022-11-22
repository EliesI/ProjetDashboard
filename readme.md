# Le mauvais coin BACKEND

## INSTALLATION

1)clonner l'archive et l'ouvrir dans un IDE depuis la branche developp.  

2)verifier que le projet est bien en version Java 17.  

3)Vérifier dans le fichier se trouvant dans src > main > ressources > application.properties que les parametres suivant soit à jour avec votre BDD:  

spring.datasource.url=jdbc:mysql://localhost:3306/doubledash?createDatabaseIfNotExist=true&serverTimezone=CET
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

4) Executer la commande "mvn clean install" nb: pour utiliser les commandes mvn sous Windows il faut installer maven ou l'executer grace à un IDE telle que IntellJ ou Eclipse.  

5) Lancer le back-office avec la commande "mvn spring-boot:run"

# Le mauvais coin FRONTEND

## Installation

1) Clonner le répertoire depuis la branche developp

2) Installer Node JS sur le site officiel (https://nodejs.org/en/download/)

3) Lancer l'invite de commande (CMD)

4) Installer Angular avec la commande suivante 'npm install -g @angular/cli'

5) Naviguer vers le dossier du projet avec la cmd (ex : cd Documents, cd GitHub, cd T-JAV-501-LIL-5-1-dashboard-lucas.zielinski)

6) Installer les modules du projet avec la commande 'npm install'

7) Lancer le projet avec la commande 'npm start'

8) Accéder au site à l'adresse 'http://localhost:8081/'

# Double dash FONCTIONNALITÉS

Authentification oAUTH2

En tant qu'utilisateur :

    - S'abonner à des widgets, les modifier, supprimer
    - Modifier son profil

En tant qu'admin :

    - Modifier, supprimer un user

