## Application Chat system

## Déploiement de l'application

Notre application n'a pas pu être mise sous format .jar à cause de l'utilisation d'une base de donnée centralisée.
Pour lancer notre application il suffit donc de cloner notre github et de lancer les commandes suivantes depuis le fichier racine de notre arborescence (COO-POO) :
* # javac *.java
* puis # java -cp .:mysql-connector.jar main

Une fois la seconde commande lancée, on se situe sur l'écran de connexion à l'application.

## Connexion en tant qu'administrateur
Entrer Login : Admin
Mot de passe : Admin

Ainsi vous pouvez rajouter un utilisateur ou bien voir les informations sur un utilisateur en cliquant sur la liste de gauche présentanrt l'ensemble des utilisateurs (le bouton de désactivation d'un utilisateur n'est pas activé).

## Connexion en tant qu'utilisateur
Entrer un des login/mot de passe présent dans la base de donnée à savoir :
Thomas/Thomas
Pierre/Pierre
Gerard/Gerard
Jean/Jean

Possibilité aussi de créer un User depuis l'interface admin et ensuite se conneter avec celui-ci.

## Utilisation de la parti clavardage

L'interface de chat est très simple :
* Sur la gauche, on voit la liste des users connectés. Un click sur l'un d'eux affiche la conversation que nous avons eu la dernière fois avec cet utilisateur et on peut lui parler.
* Au milieu on a 3 zones; la fenêtre de chat, la zone de DragNDrop de fichier et le bouton pour envoyer le fichier puis en dessous la zone pour entrer le texte a envoyé et le bouton d'envoi du texte.
* Sur la droite, si aucun fichier n'est reçu il n'y a rien mais quand un fichier est reçu, il s'affiche dans une liste (ressemblante avec celle des utilisateurs connectés) et on peut cliquer dessus pour l'ouvrir (fichier limités à 60Ko).
