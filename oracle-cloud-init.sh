#!/bin/bash

# Log everything
exec > >(tee -a /var/log/cloud-init-custom.log)
exec 2>&1

echo "=== Starting cloud-init script ==="
date

# Update system
echo "Updating system..."
apt-get update
apt-get upgrade -y

# Install Docker and docker-compose
echo "Installing Docker and docker-compose..."
apt-get install -y docker.io docker-compose git

# Start Docker
echo "Starting Docker..."
systemctl start docker
systemctl enable docker

# Add ubuntu user to docker group
echo "Adding ubuntu to docker group..."
usermod -aG docker ubuntu

# Clone and start server
echo "Cloning repository..."
cd /home/ubuntu
git clone https://github.com/AbuCodingAI/MineServer.git
cd MineServer

echo "Starting docker-compose..."
docker-compose up -d

echo "=== Cloud-init complete ==="
date
