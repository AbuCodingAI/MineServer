@echo off
setlocal enabledelayedexpansion

echo Building Minecraft server artifacts locally...

REM Check if docker is running
docker ps >nul 2>&1
if errorlevel 1 (
    echo ERROR: Docker is not running. Please start Docker Desktop.
    exit /b 1
)

REM Clean up old data
echo Cleaning up old build artifacts...
if exist server-data rmdir /s /q server-data
mkdir server-data

REM Start the server container
echo Starting server container with 1GB memory...
docker run -d --name mineserver-build ^
    -e EULA=TRUE ^
    -e TYPE=paper ^
    -e VERSION=latest ^
    -e MEMORY=1G ^
    -e DIFFICULTY=normal ^
    -e GAMEMODE=survival ^
    -e MOTD="Welcome to Abdullah's server!" ^
    -e JVM_OPTS="-XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+ParallelRefProcEnabled" ^
    -v "%cd%\server-data:/data" ^
    -v "%cd%\plugins:/plugins:ro" ^
    itzg/minecraft-server:latest

if errorlevel 1 (
    echo ERROR: Failed to start container
    exit /b 1
)

echo Container started. Waiting for initialization...
echo This will take 5-15 minutes.
echo.

REM Wait for completion by checking logs
set "count=0"
:wait_loop
set /a count=count+1
if !count! gtr 180 (
    echo Timeout reached after 15 minutes
    goto stop_container
)

docker logs mineserver-build 2>nul | findstr /i "Done" >nul
if not errorlevel 1 (
    echo Server initialization complete!
    goto stop_container
)

docker logs mineserver-build 2>nul | findstr /i "For help, type" >nul
if not errorlevel 1 (
    echo Server initialization complete!
    goto stop_container
)

timeout /t 5 /nobreak >nul
goto wait_loop

:stop_container
echo.
echo Stopping server container...
docker stop mineserver-build >nul 2>&1
docker rm mineserver-build >nul 2>&1

echo.
echo Build complete!
echo Server artifacts saved to ./server-data/
echo.
echo Next steps:
echo 1. Verify server-data contains all necessary files
echo 2. Run: git add server-data/ build-server.bat Dockerfile .gitignore
echo 3. Run: git commit -m "Add pre-built server artifacts"
echo 4. Run: git push
