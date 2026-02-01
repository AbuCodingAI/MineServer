FROM itzg/minecraft-server:latest

ENV EULA=TRUE
ENV TYPE=paper
ENV VERSION=latest
ENV MEMORY=512M
ENV DIFFICULTY=normal
ENV GAMEMODE=survival
ENV MOTD="Welcome to Abdullah's server!"
ENV JVM_OPTS="-XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+ParallelRefProcEnabled"

EXPOSE 25565

VOLUME ["/data"]
