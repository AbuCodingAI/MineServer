# Build script to pre-generate server artifacts locally
# Run this in admin PowerShell to fully initialize the server

$ErrorActionPreference = "Stop"

Write-Host "Building Minecraft server artifacts locally..."

# Check if docker is running
docker ps > $null 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Docker is not running. Please start Docker Desktop."
    exit 1
}

# Clean up old server-data if it exists
if (Test-Path "server-data") {
    Write-Host "Removing old server-data..."
    Remove-Item -Recurse -Force "server-data"
}

# Create server-data directory
New-Item -ItemType Directory -Path "server-data" -Force > $null

# Start the server container
Write-Host "Starting server container with 1GB memory..."
$containerId = docker run -d `
    -e EULA=TRUE `
    -e TYPE=paper `
    -e VERSION=latest `
    -e MEMORY=1G `
    -e DIFFICULTY=normal `
    -e GAMEMODE=survival `
    -e MOTD="Welcome to Abdullah's server!" `
    -e JVM_OPTS="-XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+ParallelRefProcEnabled" `
    -v "${PWD}/server-data:/data" `
    -v "${PWD}/plugins:/plugins" `
    itzg/minecraft-server:latest

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Failed to start container"
    exit 1
}

Write-Host "Container ID: $containerId"
Write-Host "Waiting for server to fully initialize..."
Write-Host "This will take 5-15 minutes. Watching logs..."
Write-Host ""

# Stream logs and wait for completion
$maxWait = 900  # 15 minutes
$elapsed = 0
$lastLogLine = 0

while ($elapsed -lt $maxWait) {
    # Get all logs
    $logs = docker logs $containerId 2>&1 | Where-Object { $_ }
    $logLines = @($logs)
    
    # Print new log lines
    if ($logLines.Count -gt $lastLogLine) {
        $newLines = $logLines[$lastLogLine..($logLines.Count - 1)]
        foreach ($line in $newLines) {
            Write-Host $line
        }
        $lastLogLine = $logLines.Count
    }
    
    # Check if server is running (look for "Done" or similar completion message)
    $logsText = $logs -join " "
    if ($logsText -match "Done" -or $logsText -match "For help, type" -or $logsText -match "Server started") {
        Write-Host ""
        Write-Host "Server initialization complete!"
        break
    }
    
    Start-Sleep -Seconds 5
    $elapsed += 5
}

if ($elapsed -ge $maxWait) {
    Write-Host ""
    Write-Host "WARNING: Timeout reached. Stopping container anyway..."
}

Write-Host ""
Write-Host "Stopping server container..."
docker stop $containerId > $null
docker rm $containerId > $null

Write-Host "Build complete!"
Write-Host "Server artifacts saved to ./server-data/"
Write-Host ""
Write-Host "Next steps:"
Write-Host "1. Verify server-data contains all necessary files"
Write-Host "2. Run: git add server-data/ build-server.ps1 Dockerfile .gitignore"
$msg = "3. Run: git commit -m " + "'Add pre-built server artifacts'"
Write-Host $msg
Write-Host "4. Run: git push"
