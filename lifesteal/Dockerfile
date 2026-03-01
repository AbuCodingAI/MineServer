FROM itzg/minecraft-server:latest

ENV EULA=TRUE
ENV TYPE=PAPER
ENV VERSION=1.21.11
ENV MEMORY=8G
ENV JVM_XX_OPTS="-XX:+UseG1GC -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200 -XX:+UnlockExperimentalVMOptions -XX:G1NewCollectionHeuristicPercent=30 -XX:G1ReservePercent=20 -XX:G1HeapRegionSize=32M"

EXPOSE 25565

VOLUME ["/data"]
