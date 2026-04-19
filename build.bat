@echo off

REM Check JAVA_HOME is set
IF "%JAVA_HOME%"=="" (
    echo ERROR: JAVA_HOME is not set. Please set it to your JDK installation.
    pause
    exit /b 1
)

echo [1/3] Compiling...
javac -d bin -cp ".;antlr_4.9.3.jar" -sourcepath src src/CubeCode.java
IF %ERRORLEVEL% NEQ 0 ( echo Compilation failed. & pause & exit /b 1 )

echo [2/3] Creating JAR...
cd bin
"%JAVA_HOME%\bin\jar" xf ..\antlr_4.9.3.jar
cd ..
"%JAVA_HOME%\bin\jar" cfe CubeCode.jar CubeCode -C bin .
IF %ERRORLEVEL% NEQ 0 ( echo JAR creation failed. & pause & exit /b 1 )

echo [3/3] Packaging as .exe...
"%JAVA_HOME%\bin\jpackage" --input . ^
    --name CubeCode ^
    --main-jar CubeCode.jar ^
    --main-class CubeCode ^
    --type app-image ^
    --win-console ^
    --dest output
IF %ERRORLEVEL% NEQ 0 ( echo Packaging failed. & pause & exit /b 1 )

echo Done! Check the 'output' folder for your installer.
pause