@echo off
cd /d %~dp0\..
if exist out rmdir /s /q out
mkdir out
for /r src\main\java %%f in (*.java) do echo %%f>>sources.txt
javac -encoding UTF-8 -source 11 -target 11 -d out @sources.txt
if errorlevel 1 goto :error
del sources.txt
java -cp out mx.edu.tecmilenio.sicoro.Main
goto :end
:error
echo Ocurrio un error de compilacion.
:end
pause
