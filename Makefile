IDLCC = idlj
IDL_FLAGS = -fall
IDL_FILES = Bank.idl InterBank.idl
IDL_FILE_NAMES = $(basename $(IDL_FILES))

PORT = 2810
HOST = localhost
NAME_SERVICE = NameService

TRASH = $(foreach file, $(IDL_FILE_NAMES), $(file)POA.java $(file)Helper.java $(file)Holder.java $(file)Operations.java _$(file)Stub.java $(file).java)

RESTLET        := .
HTTPCOMPONENTS := .

HTTPCOMPONENTS_CP := $(HTTPCOMPONENTS)/lib/httpclient-4.3.6.jar:$(HTTPCOMPONENTS)/lib/httpcore-4.3.3.jar:$(HTTPCOMPONENTS)/lib/commons-logging-1.1.3.jar

RESTLET_CP := $(RESTLET)/lib/org.restlet.jar:$(RESTLET)/lib/org.restlet.ext.jaxrs.jar:$(RESTLET)/lib/javax.ws.rs_1.1/javax.ws.rs.jar

CLASSPATH = .:$(RESTLET_CP):$(HTTPCOMPONENTS_CP)



all : $(IDL_FILES)
	idlj -fall Bank.idl
	idlj -fall InterBank.idl	
	javac -cp $(CLASSPATH) *.java

run-server :
	tnameserv -ORBInitialPort $(PORT) &
	java BankServer -ORBInitRef NameService=corbaloc::$(HOST):$(PORT)/$(NAME_SERVICE)

client :
	java BankClient -ORBInitRef NameService=corbaloc::$(HOST):$(PORT)/$(NAME_SERVICE)
clean :
	rm -f *~ $(TRASH) *.class
