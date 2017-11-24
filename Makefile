IDLCC = idlj
IDL_FLAGS = -fall -verbose
IDL_FILES = Bank.idl
IDL_FILE_NAMES = $(basename $(IDL_FILES))
IDL_DIR = idl
MODULE_IDL = project

BUILD = build/
BANK_DIR = bank/
REST_DIR = rest/
TEST_DIR = test/
BANK1 = BNP
BANK2 = CA

TEST = test

PORT = 2810
ORB_ACTIVATION_PORT=2809

HOST = localhost
NAME_SERVICE = NameService



TRASH = $(BANK_DIR)$(MODULE_IDL)

RESTLET        := .
HTTPCOMPONENTS := .

HTTPCOMPONENTS_CP := $(HTTPCOMPONENTS)/lib/httpclient-4.3.6.jar:$(HTTPCOMPONENTS)/lib/httpcore-4.3.3.jar:$(HTTPCOMPONENTS)/lib/commons-logging-1.1.3.jar

RESTLET_CP := $(RESTLET)/lib/org.restlet.jar:$(RESTLET)/lib/org.restlet.ext.jaxrs.jar:$(RESTLET)/lib/javax.ws.rs_1.1/javax.ws.rs.jar

CLASSPATH = .:$(RESTLET_CP):$(HTTPCOMPONENTS_CP):$(BUILD)

vpath %.java test bank rest



ORBD=orbd -ORBInitialPort ${PORT} -port ${ORB_ACTIVATION_PORT} -serverPollingTime 200 -serverStartupDelay 1000
SERVERTOOL=servertool

SERIAL_FILE = file

all : clean-file
	idlj $(IDL_FLAGS) -td $(BANK_DIR) $(IDL_DIR)/Bank.idl
	javac -d $(BUILD) -cp $(BANK_DIR) $(BANK_DIR)*.java
	javac -d $(BUILD) -cp $(CLASSPATH) $(REST_DIR)*.java

run-rest : 
	java -cp $(CLASSPATH) BankRestServer

run-server :
	tnameserv -ORBInitialPort $(PORT) &
	java -cp $(BUILD) InterBankServer -ORBInitRef NameService=corbaloc::$(HOST):$(PORT)/$(NAME_SERVICE) &
	sleep 2
	java -cp $(BUILD) BankServer $(BANK1) -ORBInitRef NameService=corbaloc::$(HOST):$(PORT)/$(NAME_SERVICE) &
	java -cp $(BUILD) BankServer $(BANK2) -ORBInitRef NameService=corbaloc::$(HOST):$(PORT)/$(NAME_SERVICE) &

servertool:
	$(SERVERTOOL) -ORBInitialPort $(PORT) 

run-orbd :
	$(ORBD)
client :
	javac -d $(BUILD) -cp $(BUILD) bank/BankClient.java
	java -cp $(BUILD) BankClient -ORBInitRef NameService=corbaloc::$(HOST):$(PORT)/$(NAME_SERVICE)

observer : Observer.java
	javac -d $(BUILD) -cp $(BUILD) $<
	java -cp $(BUILD) Observer -ORBInitRef NameService=corbaloc::$(HOST):$(PORT)/$(NAME_SERVICE)

test : test-bank

test-bank : TestBank.java
	javac -d $(BUILD) -cp $(BUILD) $<
	java -cp $(BUILD) -ea TestBank -ORBInitRef NameService=corbaloc::$(HOST):$(PORT)/$(NAME_SERVICE)

clean-file :
	rm -f $(SERIAL_FILE)
clean : 
	-killall -q tnameserv
	-killall -q java
	rm -rf *~ $(TRASH) *.class $(BUILD)*
