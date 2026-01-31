FROM itzg/minecraft-server:latest

ENV EULA=TRUE
ENV TYPE=paper
ENV VERSION=latest
ENV MEMORY=2G
ENV DIFFICULTY=normal
ENV GAMEMODE=survival
ENV MOTD="Welcome to Abdullah's server!"

EXPOSE 25565

VOLUME ["/data"]
