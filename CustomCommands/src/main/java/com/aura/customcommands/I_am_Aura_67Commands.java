package com.aura.customcommands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class I_am_Aura_67Commands implements CommandExecutor {

    private final JavaPlugin plugin;
    private static final int SIZE = 30;

    public I_am_Aura_67Commands(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.getName().equals("I_am_Aura_67")) {
            player.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        switch (command.getName().toLowerCase()) {
            case "spawnancientdebris":
                spawnAncientDebris(player);
                return true;
            case "spawndiamonds":
                spawnDiamonds(player);
                return true;
            case "makebox":
                makeBox(player);
                return true;
        }

        return false;
    }

    private void spawnAncientDebris(Player player) {
        var location = player.getLocation();
        var world = player.getWorld();
        int startX = location.getBlockX();
        int startY = location.getBlockY();
        int startZ = location.getBlockZ();

        for (int x = startX; x < startX + SIZE; x++) {
            for (int y = startY; y < startY + SIZE; y++) {
                for (int z = startZ; z < startZ + SIZE; z++) {
                    world.getBlockAt(x, y, z).setType(Material.ANCIENT_DEBRIS);
                }
            }
        }

        player.sendMessage("§aSpawned 30x30x30 Ancient Debris cube!");
    }

    private void spawnDiamonds(Player player) {
        var location = player.getLocation();
        var world = player.getWorld();
        int startX = location.getBlockX();
        int startY = location.getBlockY();
        int startZ = location.getBlockZ();

        for (int x = startX; x < startX + SIZE; x++) {
            for (int y = startY; y < startY + SIZE; y++) {
                for (int z = startZ; z < startZ + SIZE; z++) {
                    world.getBlockAt(x, y, z).setType(Material.DIAMOND_BLOCK);
                }
            }
        }

        player.sendMessage("§aSpawned 30x30x30 Diamond cube!");
    }

    private void makeBox(Player player) {
        var location = player.getLocation();
        var world = player.getWorld();
        int startX = location.getBlockX();
        int startY = location.getBlockY();
        int startZ = location.getBlockZ();
        int endX = startX + SIZE - 1;
        int endY = startY + SIZE - 1;
        int endZ = startZ + SIZE - 1;

        // Fill entire area with air first
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    world.getBlockAt(x, y, z).setType(Material.AIR);
                }
            }
        }

        // Create wooden plank shell (outer walls only)
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    // Check if block is on the edge
                    if (x == startX || x == endX || y == startY || y == endY || z == startZ || z == endZ) {
                        world.getBlockAt(x, y, z).setType(Material.OAK_PLANKS);
                    }
                }
            }
        }

        player.sendMessage("§aCreated 30x30x30 hollow box with wooden plank walls!");
    }
}
