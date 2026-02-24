FROM itzg/minecraft-server:latest

ENV EULA=TRUE
ENV TYPE=paper
ENV VERSION=latest
ENV MEMORY=10G
ENV DIFFICULTY=normal
ENV GAMEMODE=survival
ENV MOTD="Welcome to King SMP"
ENV ONLINE_MODE=FALSE
ENV JVM_OPTS="-XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+ParallelRefProcEnabled"

EXPOSE 25565

VOLUME ["/data"]

COPY plugins/ /plugins/
