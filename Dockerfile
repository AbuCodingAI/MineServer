FROM itzg/minecraft-server:latest

ENV EULA=TRUE
ENV TYPE=paper
ENV VERSION=latest
ENV MEMORY=1G
ENV DIFFICULTY=normal
ENV GAMEMODE=survival
ENV MOTD=Welcome to my Minecraft server!

EXPOSE 25565

VOLUME ["/data"]
