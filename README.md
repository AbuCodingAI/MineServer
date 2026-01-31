# Minecraft Paper Server

A Docker-based Minecraft Paper server with EssentialsX plugin support.

## Local Setup

1. Install Docker and Docker Compose
2. Download EssentialsX.jar from [essentialsx.net/downloads](https://essentialsx.net/downloads.html)
3. Place it in the `plugins/` folder
4. Run: `docker-compose up`
5. Connect to `localhost:25565`

## Deployment to Render

1. Push this repo to GitHub
2. Create a Web Service on Render
3. Connect your GitHub repo
4. Set Runtime to Docker
5. Deploy

## Customization

Edit `docker-compose.yml` to change:
- `MEMORY` - Server RAM (e.g., 2G, 4G)
- `DIFFICULTY` - peaceful, easy, normal, hard
- `GAMEMODE` - survival, creative, adventure
- `MOTD` - Server message of the day
