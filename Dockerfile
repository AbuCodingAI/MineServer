FROM itzg/minecraft-server:latest

ENV EULA=TRUE
ENV TYPE=vanilla
ENV VERSION=latest
ENV MEMORY=450M
ENV DIFFICULTY=normal
ENV GAMEMODE=survival
ENV MOTD="Welcome to Abdullah's server!"
ENV JVM_OPTS="-XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+ParallelRefProcEnabled"
ENV HEALTHCHECK_DISABLED=true

EXPOSE 25565

VOLUME ["/data"]
