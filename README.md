# Application Chat system

## Déploiement de l'application

Notre application n'a pas pu être mise sous format .jar à cause de l'utilisation d'une base de donnée centralisée.
Pour lancer notre application il suffit donc de faire la commande suivante dans un terminal depuis le dossier racine de notre arborescence (COO-POO) :
* ./KitChat
* ou simplement double cliquer sur le fichier KitChat puis sur démarrer si votre système d'exploitation le propose.

So jamais ça ne se lance vriment pas même avec la commande sur le terminal, vous pouvez taper cette commande sur le terminal dans le dossier src :
$ java -cp .:mysql-connector.jar main

Une fois la commande lancée, on se situe sur l'écran de connexion à l'application.

## Connexion en tant qu'administrateur
Entrer Login : Admin
Mot de passe : Admin

Ainsi vous pouvez rajouter un utilisateur ou bien voir les informations sur un utilisateur en cliquant sur la liste de gauche présentanrt l'ensemble des utilisateurs (le bouton de désactivation d'un utilisateur n'est pas activé).

## Connexion en tant qu'utilisateur
Entrer un des login/mot de passe présent dans la base de donnée à savoir :

Thomas/Thomas,
Pierre/Pierre,
Gerard/Gerard,
Jean/Jean

Possibilité aussi de créer un User depuis l'interface admin et ensuite se connecter avec celui-ci.

## Utilisation de la parti clavardage

L'interface de chat est très simple :
* Sur la gauche, on voit la liste des users connectés. Un click sur l'un d'eux affiche la conversation que nous avons eu la dernière fois avec cet utilisateur et on peut lui parler.
* Au milieu on a 3 zones; la fenêtre de chat, la zone de DragNDrop de fichier et le bouton pour envoyer le fichier puis en dessous la zone pour entrer le texte a envoyé et le bouton d'envoi du texte.
* Sur la droite, si aucun fichier n'est reçu il n'y a rien mais quand un fichier est reçu, il s'affiche dans une liste (ressemblante avec celle des utilisateurs connectés) et on peut cliquer dessus pour l'ouvrir (fichier limités à 60Ko).

## Deconnexion

La déconnexion se fait automatiquement en quittant l'application en cliquant sur la petite croix de fermeture de fenêtre.
