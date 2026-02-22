# Remote NoCheat Script - Destroys all command blocks via RCON
# Usage: .\nocheats-remote.ps1

param(
    [string]$Host = "129.146.103.14",
    [string]$KeyPath = "oracle-key.pem",
    [string]$User = "ubuntu"
)

Write-Host "Executing /nocheats command on remote server..." -ForegroundColor Cyan

ssh -i $KeyPath "$User@$Host" "docker exec mineserver_minecraft_1 rcon-cli nocheats"

Write-Host "Command executed!" -ForegroundColor Green
