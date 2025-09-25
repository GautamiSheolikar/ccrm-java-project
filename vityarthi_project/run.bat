@echo off
chcp 65001
title CCRM Project Runner

echo ========================================
echo    CCRM Project - Compile and Run
echo ========================================
echo.

echo [1/8] Ensuring data files are in correct location...
if exist students.csv (
    if not exist test_data mkdir test_data
    move students.csv test_data\ 2>nul
)
if exist courses.csv (
    if not exist test_data mkdir test_data
    move courses.csv test_data\ 2>nul
)

echo [2/8] Creating output directory...
if not exist out mkdir out

echo [3/8] Compiling domain classes...
javac -d out src\edu\ccrm\domain\*.java
if errorlevel 1 goto error

echo [4/8] Compiling config classes...
javac -d out -cp out src\edu\ccrm\config\*.java
if errorlevel 1 goto error

echo [5/8] Compiling service classes...
javac -d out -cp out src\edu\ccrm\service\*.java
if errorlevel 1 goto error

echo [6/8] Compiling io classes...
javac -d out -cp out src\edu\ccrm\io\*.java
if errorlevel 1 goto error

echo [7/8] Compiling cli and main classes...
javac -d out -cp out src\edu\ccrm\cli\*.java src\edu\ccrm\CCRMMain.java
if errorlevel 1 goto error

echo [8/8] Starting application...
echo.
echo ✅ COMPILATION SUCCESSFUL!
echo.
echo ========================================
java -cp out edu.ccrm.CCRMMain

pause
goto end

:error
echo.
echo ❌ COMPILATION FAILED!
pause
goto end

:end