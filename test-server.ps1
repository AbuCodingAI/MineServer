# Test Minecraft server port every 3 minutes with timestamps

$ip = "129.146.103.14"
$port = 25565
$logFile = "server-test-log.txt"

Write-Host "Starting port tests for $ip`:$port every 3 minutes..."
Write-Host "Logging to $logFile"
Write-Host ""

while ($true) {
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $result = Test-NetConnection -ComputerName $ip -Port $port -WarningAction SilentlyContinue
    
    if ($result.TcpTestSucceeded) {
        $status = "OPEN"
        $color = "Green"
    } else {
        $status = "CLOSED"
        $color = "Red"
    }
    
    $logEntry = "$timestamp - Port $($port): $status"
    Write-Host $logEntry -ForegroundColor $color
    Add-Content -Path $logFile -Value $logEntry
    
    Start-Sleep -Seconds 180
}
