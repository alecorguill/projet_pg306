IDLCC = idlj
IDL_FLAGS = -fall
IDL_FILES = Bank.idl InterBank.idl
IDL_FILE_NAMES = $(basename $(IDL_FILES))

PORT = 2810
HOST = localhost
NAME_SERVICE = NameService

TRASH = $(foreach file, $(IDL_FILE_NAMES), $(file)POA.java $(file)Helper.java $(file)Holder.java $(file)Operations.java _$(file)Stub.java $(file).java)

all : $(IDL_FILES)
	idlj -fall Bank.idl
	idlj -fall InterBank.idl	
	javac *.java

run-server :
	tnameserv -ORBInitialPort $(PORT) &
	java BankServer -ORBInitRef NameService=corbaloc::$(HOST):$(PORT)/$(NAME_SERVICE)

client :
	java BankClient -ORBInitRef NameService=corbaloc::$(HOST):$(PORT)/$(NAME_SERVICE)
clean :
	rm -f *~ $(TRASH) *.class
