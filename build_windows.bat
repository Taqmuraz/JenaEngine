rd /s /q build
dir /s /b *.java > sourcelist.txt
javac -d build @sourcelist.txt
del sourcelist.txt

cd build
(echo Main-Class: jena.swing.Program & echo. ) > manifest.txt
jar -cvfm jena.jar manifest.txt jena
cd ..
start /B run_windows.bat