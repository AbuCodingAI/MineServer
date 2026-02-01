#!/bin/bash
set -e

# Update system
apt-get update
apt-get upgrade -y

# Install Docker
apt-get install -y docker.io git curl

# Start Docker
systemctl start docker
systemctl enable docker

# Add ubuntu user to docker group
usermod -aG docker ubuntu

# Clone repository
cd /home/ubuntu
git clone https://github.com/AbuCodingAI/MineServer.git
cd MineServer

# Create data directory with proper permissions
mkdir -p minecraft_data
chmod 777 minecraft_data

# Start the server with docker-compose
docker-compose up -d

# Wait for server to be ready
echo "Waiting for Minecraft server to start..."
sleep 30

# Display logs
docker-compose logs -f minecraft
