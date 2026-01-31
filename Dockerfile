FROM itzg/minecraft-server:latest

ENV EULA=TRUE
ENV TYPE=paper
ENV VERSION=latest
ENV MEMORY=384M
ENV DIFFICULTY=normal
ENV GAMEMODE=survival
ENV MOTD="Welcome to Abdullah's server!"
ENV JVM_OPTS="-XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+ParallelRefProcEnabled -XX:G1NewCollectionHeuristicPercent=35 -XX:G1ReservePercent=20 -XX:InitiatingHeapOccupancyPercent=20 -XX:+DisableExplicitGC"
ENV RCON_CMDS_STARTUP="/say Server started"
ENV ENABLE_RCON=true
ENV RCON_PORT=25575

EXPOSE 25565 25575

VOLUME ["/data"]
