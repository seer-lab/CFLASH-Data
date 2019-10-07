.PHONY: run compile clear

JUNIT_JAR=../../../lib/JUnit/junit-4.12.jar
HAMCREST_JAR=../../../lib/Hamcrest/hamcrest-core-1.3.jar
CLASSP=./$(JUNIT_JAR):./$(HAMCREST_JAR):.

all: run clear

TESTS_MAIN=RunTests
test: compile
	@java -cp $(CLASSP) $(TESTS_MAIN)

MAIN=Bank
INIT_BALANCE=1000
NUM_THREADS=5
run: compile
	@java -cp $(CLASSP) $(MAIN) $(INIT_BALANCE) $(NUM_THREADS)

compile:
	@javac -cp $(CLASSP) *.java

clear:
	@rm *.class