package com.aura.lifesteal;

import org.bukkit.plugin.java.JavaPlugin;

public class LifestealPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        LifestealListener listener = new LifestealListener();

        getServer().getPluginManager().registerEvents(listener, this);
        getLogger().info("Lifesteal plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Lifesteal plugin disabled!");
    }
}
