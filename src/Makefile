all: tojar

run:
	java -jar Main.jar

tojar:
	javac *.java -d .
	jar cfe Main.jar Main *.class

clean:
	rm -rf *.class *.jar *.zip
