# Micro:bit

Documentation que nous avons utilisé pour coder en C++ : 
* https://lancaster-university.github.io/microbit-docs/

## 1. drivers

Dossier contenant les drivers pour micro:bit à installer pour Windows.

## 2. hex

Dossier contenant le code compilé pour chaque micro:bit : emergency et simulation. Afin de le mettre en place, il suffit de copier le code sur le micro:bit concerné.

## 3. libraries

Dossier contenant les librairies que nous avons codé en C avec en plus des tests unitaires.
* ``` cipher ``` Chiffrement / déchiffrement de string avec clé.
* ``` convert ``` Convertir des **float** en **char\***.
* ``` incident ``` Objet incident afin de traiter plus aisément les données.

## 4. microbit-emergency

Dossier contenant le code C++ pour le micro:bit emergency. Vous pouvez compiler ce code sur le site : 
* https://ide.mbed.com/compiler/

## 5. microbit-simulation

Dossier contenant le code C++ pour le micro:bit simulation. Vous pouvez compiler ce code sur le site : 
* https://ide.mbed.com/compiler/

## 6. schema

Dossier contenant le schéma du protocole de communication utilisé par les micor:bit.

## 7. server

Dossier contenant un server de test en Python permettant d'envoyer des incidents au micor:bit via UART/Serial.
