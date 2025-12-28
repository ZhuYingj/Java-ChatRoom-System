# Un système de chat client-serveur développé en Java utilisant les sockets et les threads.
Ce projet implémente une application de clavardage (chat) complète avec architecture client-serveur. Le système permet à plusieurs utilisateurs de se connecter simultanément, d'échanger des messages en temps réel, et de conserver un historique complet des conversations.

# 💻 Utilisation
Démarrage du serveur
Exécutez le serveur :
-jar serveur.jar

Entrez les paramètres demandés :
- Adresse IP : L'adresse IP de votre machine (format xxx.xxx.xxx.xxx)
- Port d'écoute : Un port entre 5000 et 5050

Connexion du client
Exécutez le client :
-jar client.jar

Entrez les informations de connexion :
- Adresse IP du serveur : L'adresse où le serveur est hébergé
- Port du serveur : Le port d'écoute du serveur (5000-5050)
- Nom d'utilisateur : Votre identifiant
- Mot de passe : Votre mot de passe

Une fois connecté, vous pouvez :
- Voir les 15 derniers messages
- Envoyer des messages (max 200 caractères)
- Recevoir les messages des autres utilisateurs en temps réel

# 🛠️ Technologies
- Java et Sockets TCP/IP
- Concurrence : Threads Java
