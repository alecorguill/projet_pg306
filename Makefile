IDLCC = idlj
IDL_FLAGS = -fall
IDL_FILES = Bank.idl
IDL_FILE_NAMES = $(basename $(IDL_FILES))
IDL_DIR = idl
MODULE_IDL = project

BUILD = build/
BANK_DIR = bank/
REST_DIR = rest/
BANK1 = BNP
BANK2 = CA

TEST = test

PORT = 2810
HOST = localhost
NAME_SERVICE = NameService



TRASH = $(BANK_DIR)$(MODULE_IDL)

RESTLET        := .
HTTPCOMPONENTS := .

HTTPCOMPONENTS_CP := $(HTTPCOMPONENTS)/lib/httpclient-4.3.6.jar:$(HTTPCOMPONENTS)/lib/httpcore-4.3.3.jar:$(HTTPCOMPONENTS)/lib/commons-logging-1.1.3.jar

RESTLET_CP := $(RESTLET)/lib/org.restlet.jar:$(RESTLET)/lib/org.restlet.ext.jaxrs.jar:$(RESTLET)/lib/javax.ws.rs_1.1/javax.ws.rs.jar

CLASSPATH = .:$(RESTLET_CP):$(HTTPCOMPONENTS_CP):$(BUILD)



all :
	idlj -td $(BANK_DIR) -fall $(IDL_DIR)/Bank.idl
	javac -d $(BUILD) -cp $(BANK_DIR) $(BANK_DIR)*.java

rest : 
	javac -d build -cp $(CLASSPATH) $(REST_DIR)*.java

run-server :
	tnameserv -ORBInitialPort $(PORT) &
	java -cp $(BUILD) InterBankServer -ORBInitRef NameService=corbaloc::$(HOST):$(PORT)/$(NAME_SERVICE) &
	sleep 2
	java -cp $(BUILD) BankServer $(BANK1) -ORBInitRef NameService=corbaloc::$(HOST):$(PORT)/$(NAME_SERVICE) &
client :
	javac -d $(BUILD) -cp $(BUILD) bank/BankClient.java
	java -cp $(BUILD) BankClient -ORBInitRef NameService=corbaloc::$(HOST):$(PORT)/$(NAME_SERVICE)
clean : 
	-killall -q tnameserv
	-killall -q java
	rm -rf *~ $(TRASH) *.class $(BUILD)*
