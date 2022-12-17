dir /s /b *.java > sourcelist.txt
javac -d build @sourcelist.txt
del sourcelist.txt

rd /s /q build