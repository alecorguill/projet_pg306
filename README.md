####### Compilation execution ######

Toujours faire un make clean avant de compiler :
     make clean	 	 
Pour compiler source + idl :
     make
Pour lancer le serveur. Par defaut deux banques sont lancées.

     make run-server

####### Observateur ######

Une fois le serveur lancé on peut lancer l'observateur pour afficher l'état de
l'interbanque (Autant de fois qu'on veut).

	make observer

####### TESTS ######

Une fois le serveur lancée on peut lancer les tests.

    make test

Au total en partant de rien pour lancer les tests :

   make clean
   make
   make run-server
   // Dans un autre terminal
   make test

####### REST ######

Il est nécessaire d'avoir lancer le serveur pour faire du REST

   make clean
   make 
   make run-server
   // Dans un autre terminal
   make run-rest
   // Dans un autre terminal 
   make test-rest
