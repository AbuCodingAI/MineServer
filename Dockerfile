FROM itzg/minecraft-server:latest

ENV EULA=TRUE
ENV TYPE=paper
ENV VERSION=latest
ENV MEMORY=450M
ENV DIFFICULTY=normal
ENV GAMEMODE=survival
ENV MOTD="Welcome to Abdullah's server!"
ENV JVM_OPTS="-XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+ParallelRefProcEnabled -XX:G1NewCollectionHeuristicPercent=35 -XX:G1ReservePercent=20 -XX:InitiatingHeapOccupancyPercent=20 -XX:+DisableExplicitGC"

EXPOSE 25565

VOLUME ["/data"]
