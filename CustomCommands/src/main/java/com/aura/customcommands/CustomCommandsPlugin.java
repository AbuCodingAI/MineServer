package com.aura.customcommands;

import org.bukkit.plugin.java.JavaPlugin;

public class CustomCommandsPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("spawnancientdebris").setExecutor(new I_am_Aura_67Commands(this));
        getCommand("spawndiamonds").setExecutor(new I_am_Aura_67Commands(this));
        getCommand("makebox").setExecutor(new I_am_Aura_67Commands(this));
        getLogger().info("CustomCommands plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("CustomCommands plugin disabled!");
    }
}
