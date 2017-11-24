########### PARTIE PERSISTANCE #############

########### Organisation du répertoire ##########

/bank/ : Les sources du service interbanquaire

/build/ : repertoire des fichiers compilés

/idl/ : fichiers idl

########### Compilation execution #########

    make

    make run-orbd

// Dans un autre terminal

   make servertool

// Dans l'invite de commande ecrire 

   register -server BankServer -applicationName bank -classpath build -args BNP

// Dans un autre terminal

   make client

// Ceci va appeler quelques méthodes du serveur. On peut shutdown le serveur et le

// relancer pour verifier qu'il y a bien persistance des données.

// Voici un exemple de commandes qui permettent de s'en rendre compte.

// Dans servertool

// Pour avoir l'id du serveur

   list

// Utiliser l'id vu par la commande list

   shutdown -serverid <id_du_serveur>
   startup -serverid <id_du_serveur>

// Puis relancer le petit client

   make client

// On voit que les comptes créés avant le shutdown sont toujours présents.