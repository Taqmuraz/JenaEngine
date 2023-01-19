find . -name "*.java" > sourcelist.txt
rm -r build
javac -d build @sourcelist.txt
rm sourcelist.txt
cd build
(echo & echo Main-Class: jena.main.Program) > manifest.txt
jar -cvfm jena.jar manifest.txt jena
cd ..
sh run_linux.sh