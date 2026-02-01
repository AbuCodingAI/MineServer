#!/bin/bash
set -e

# Update system
apt-get update
apt-get upgrade -y

# Install Docker
apt-get install -y docker.io git

# Start Docker
systemctl start docker
systemctl enable docker

# Add ubuntu user to docker group
usermod -aG docker ubuntu

# Clone and start server
cd /home/ubuntu
git clone https://github.com/AbuCodingAI/MineServer.git
cd MineServer
docker-compose up -d
