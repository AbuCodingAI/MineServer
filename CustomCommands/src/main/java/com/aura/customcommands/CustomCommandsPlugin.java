package com.aura.customcommands;

import org.bukkit.plugin.java.JavaPlugin;

public class CustomCommandsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        I_am_Aura_67Commands commands = new I_am_Aura_67Commands();
        LobbySelector lobbySelector = new LobbySelector(this);
        PlayerTagListener tagListener = new PlayerTagListener();

        if (getCommand("spawnancientdebris") != null) {
            getCommand("spawnancientdebris").setExecutor(commands);
        }
        if (getCommand("spawndiamonds") != null) {
            getCommand("spawndiamonds").setExecutor(commands);
        }
        if (getCommand("makebox") != null) {
            getCommand("makebox").setExecutor(commands);
        }
        if (getCommand("spawn") != null) {
            getCommand("spawn").setExecutor(commands);
        }
        if (getCommand("lobby") != null) {
            getCommand("lobby").setExecutor(commands);
        }
        if (getCommand("givearmor") != null) {
            getCommand("givearmor").setExecutor(commands);
        }
        if (getCommand("givesword") != null) {
            getCommand("givesword").setExecutor(commands);
        }
        if (getCommand("removewarp") != null) {
            getCommand("removewarp").setExecutor(commands);
        }

        getServer().getPluginManager().registerEvents(lobbySelector, this);
        getServer().getPluginManager().registerEvents(tagListener, this);

        getLogger().info("CustomCommands plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("CustomCommands plugin disabled!");
    }
}
