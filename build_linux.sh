find . -name "*.java" > sourcelist.txt
rm -r build
mkdir build
javac -classpath "lib/*" -d build @sourcelist.txt
rm sourcelist.txt
cd build
echo Main-Class: jena.main.Program >> manifest.txt
echo Class-Path: ../lib/jogl-all.jar ../lib/gluegen-rt.jar >> manifest.txt
jar -cvfm jena.jar manifest.txt jena
cd ..
sh run_linux.sh
