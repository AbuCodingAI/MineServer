package com.aura.avengers;

import org.bukkit.plugin.java.JavaPlugin;

public class AvengersPlugin extends JavaPlugin {

    private static AvengersPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        AvengersCommands commands = new AvengersCommands();
        MjolnirListener mjolnirListener = new MjolnirListener();
        IronManListener ironManListener = new IronManListener();
        CaptainShieldListener shieldListener = new CaptainShieldListener();
        HawkeyeListener hawkeyeListener = new HawkeyeListener();
        DisenchantingTableListener disenchantingListener = new DisenchantingTableListener();
        InfinityGauntletListener gauntletListener = new InfinityGauntletListener();

        if (getCommand("mjolnir") != null) {
            getCommand("mjolnir").setExecutor(commands);
        }
        if (getCommand("ironman") != null) {
            getCommand("ironman").setExecutor(commands);
        }
        if (getCommand("captainshield") != null) {
            getCommand("captainshield").setExecutor(commands);
        }
        if (getCommand("hawkeyearrows") != null) {
            getCommand("hawkeyearrows").setExecutor(commands);
        }

        getServer().getPluginManager().registerEvents(mjolnirListener, this);
        getServer().getPluginManager().registerEvents(ironManListener, this);
        getServer().getPluginManager().registerEvents(shieldListener, this);
        getServer().getPluginManager().registerEvents(hawkeyeListener, this);
        getServer().getPluginManager().registerEvents(disenchantingListener, this);
        getServer().getPluginManager().registerEvents(gauntletListener, this);

        CraftingListener.registerRecipes(this);
        CraftingListener.registerDisenchantingTableRecipe(this);

        getLogger().info("Avengers plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Avengers plugin disabled!");
    }

    public static AvengersPlugin getInstance() {
        return instance;
    }
}
