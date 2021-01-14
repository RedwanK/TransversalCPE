# TransversalCPE

## Objectif

L’objectif de ce projet est de réaliser d’une part un simulateur d’incendies permettant la création, le suivi et la propagation de feux de différents types (localisés sur une carte), et d’autre part de créer un dispositif de gestion de services d’urgences permettant, à partir d’informations collectées par des capteurs, de déployer et gérer les dispositifs adaptés pour éteindre les incendies.

## La centrale de simulation (au début du cycle)

Son rôle est de générer des feux dont les coordonnées, l’intensité et la fréquence sont à définir dans le programme.
Ces feux sont stockés dans une BD de simulation.
Les coordonnées et intensité de ces feux sont envoyées par ondes radio au Data Center, à l’image de détecteurs de feux qui transmettent une alerte à une centrale de supervision.

## Le Data Center

Le Data Center est la partie « métier » des services de gestion d’incendie. Il est constitué d’un ensemble de programmes allant de la réception des alertes incendies à leur prise en charge. La première action de ce groupe de programmes est donc l’acquisition des données des feux simulés et envoyées par radio (étape précédente), pour alimenter et mettre à jour une BD de feux « réels ».
Ensuite, la logique métier : un programme appelé Emergency Manager gère une flotte de services d’urgence (casernes, camions, pompiers), détecte les feux (dans la BD) et déploie les ressources nécessaires pour les éteindre (quel(s) camion(s) de quelle(s) caserne(s) est (sont) affectés à quel(s) feu(x)). Il pourra libérer les ressources affectées à un feu lorsqu’il est éteint.
Enfin, un certain nombre de données doivent être envoyées dans un serveur de stockage sur le Cloud à des fins de statistiques et visualisations.

## Les écrans de supervision des casernes

Grâce à des écrans de supervision, les casernes peuvent visualiser dans un navigateur web les feux en cours dans la ville (ou toute autre zone géographique choisie), ainsi que la position des différents véhicules de secours.
Différents contrôles permettent d’afficher / masquer les feux, les véhicules de secours, etc.
Un Dashboard affiche des statistiques en fonction de l’historique des données collectées.

## La centrale de simulation

Le simulateur détecte la présence ou non des services d’urgence près des feux (dans BD de feux réels) et réduit ou augmente leur intensité (dans la BD de feux simulés).
Les coordonnées et intensité de ces feux sont envoyées par ondes radio au Data Center…, et ainsi de suite jusqu’à…