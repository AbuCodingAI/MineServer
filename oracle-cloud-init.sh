#!/bin/bash
set -e

# Update system
apt-get update
apt-get upgrade -y

# Install Docker and dependencies
apt-get install -y docker.io git curl ufw

# Start Docker
systemctl start docker
systemctl enable docker

# Add ubuntu user to docker group
usermod -aG docker ubuntu

# Configure firewall
ufw --force enable
ufw default deny incoming
ufw default allow outgoing
ufw allow 22/tcp    # SSH
ufw allow 25565/tcp # Minecraft

# Clone repository
cd /home/ubuntu
git clone https://github.com/AbuCodingAI/MineServer.git
cd MineServer

# Create data directory with proper permissions
mkdir -p minecraft_data
chmod 755 minecraft_data

# Set secure permissions on docker-compose
chmod 644 docker-compose.yml
chmod 755 oracle-cloud-init.sh

# Start the server with docker-compose
docker-compose up -d

# Wait for server to be ready
echo "Waiting for Minecraft server to start..."
sleep 30

# Display logs
docker-compose logs minecraft
