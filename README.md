# Bank Kata Project

Ce projet est une application bancaire développée selon l'architecture hexagonale. Il permet de gérer des comptes bancaires et des livrets d'épargne avec des fonctionnalités telles que les dépôts, les retraits et les relevés mensuels.

## Prérequis

- Java 17
- Maven
- Spring Boot

## Installation

Pour installer les dépendances du projet, exécutez la commande suivante à la racine du projet :

```bash
mvn clean install
```

## Lancement de l'application

Pour démarrer l'application, utilisez la commande suivante :

```bash
mvn spring-boot:run
```
## Utilisation 

L'application expose des API REST qui peuvent être testées via des outils comme Postman ou directement via les navigateurs si des points d'entrée GET sont disponibles.

- Déposer de l'argent : POST /api/account/{accountNumber}/deposit
- Retirer de l'argent : POST /api/account/{accountNumber}/withdraw
- Consulter le solde : GET /api/account/{accountNumber}/balance
- Obtenir un relevé mensuel : GET /api/account/{accountNumber}/monthly-statement

## Tests

Pour exécuter les tests, utilisez la commande suivante :

```bash
mvn test
```

