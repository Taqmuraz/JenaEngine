find . -name "*.java" > sourcelist.txt
rm -r build
javac -d build @sourcelist.txt
rm sourcelist.txt
cd build
(echo & echo Main-Class: jena.swing.Program) > manifest.txt
jar -cvfm jena.jar manifest.txt jena
java -Xms64M -jar jena.jar
