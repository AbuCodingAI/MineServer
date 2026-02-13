package com.aura.customcommands;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class MineSystem implements Listener {
    
    private final JavaPlugin plugin;
    private final Map<String, MineLevel> mines = new HashMap<>();
    private final Map<Player, MineLevel> playerCurrentMine = new HashMap<>();
    
    public MineSystem(JavaPlugin plugin) {
        this.plugin = plugin;
        initializeMines();
    }
    
    private void initializeMines() {
        // Create mine levels similar to MineBerry/MinePeak
        mines.put("level1", new MineLevel("Level 1", 1, new Material[]{
            Material.STONE, Material.COAL_ORE, Material.COPPER_ORE
        }));
        
        mines.put("level2", new MineLevel("Level 2", 2, new Material[]{
            Material.STONE, Material.COAL_ORE, Material.COPPER_ORE, Material.IRON_ORE
        }));
        
        mines.put("level3", new MineLevel("Level 3", 3, new Material[]{
            Material.STONE, Material.COAL_ORE, Material.COPPER_ORE, Material.IRON_ORE, Material.GOLD_ORE
        }));
        
        mines.put("level4", new MineLevel("Level 4", 4, new Material[]{
            Material.STONE, Material.COAL_ORE, Material.COPPER_ORE, Material.IRON_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE
        }));
        
        mines.put("level5", new MineLevel("Level 5", 5, new Material[]{
            Material.STONE, Material.COAL_ORE, Material.COPPER_ORE, Material.IRON_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE
        }));
    }
    
    public void joinMine(Player player, String mineName) {
        MineLevel mine = mines.get(mineName.toLowerCase());
        if (mine == null) {
            player.sendMessage("§cMine not found! Available: level1, level2, level3, level4, level5");
            return;
        }
        
        playerCurrentMine.put(player, mine);
        player.sendMessage("§a[Mine] Joined " + mine.getName() + "!");
        player.sendMessage("§7Break blocks to earn money and progress!");
    }
    
    public void resetMine(String mineName) {
        MineLevel mine = mines.get(mineName.toLowerCase());
        if (mine != null) {
            mine.resetBlocks();
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        MineLevel currentMine = playerCurrentMine.get(player);
        
        if (currentMine == null) {
            return;
        }
        
        Block block = event.getBlock();
        if (currentMine.containsOre(block.getType())) {
            // Award money/experience
            int reward = calculateReward(block.getType(), currentMine.getLevel());
            player.sendMessage("§a+$" + reward);
            
            // Schedule block respawn
            scheduleBlockRespawn(block, currentMine);
        }
    }
    
    private int calculateReward(Material ore, int level) {
        int baseReward = switch (ore) {
            case COAL_ORE -> 10;
            case COPPER_ORE -> 15;
            case IRON_ORE -> 25;
            case GOLD_ORE -> 50;
            case DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE -> 100;
            default -> 5;
        };
        return baseReward * level;
    }
    
    private void scheduleBlockRespawn(Block block, MineLevel mine) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Material randomOre = mine.getRandomOre();
                block.setType(randomOre);
            }
        }.runTaskLater(plugin, 20L * 10); // 10 seconds
    }
    
    public static class MineLevel {
        private final String name;
        private final int level;
        private final Material[] ores;
        
        public MineLevel(String name, int level, Material[] ores) {
            this.name = name;
            this.level = level;
            this.ores = ores;
        }
        
        public String getName() {
            return name;
        }
        
        public int getLevel() {
            return level;
        }
        
        public boolean containsOre(Material material) {
            for (Material ore : ores) {
                if (ore == material) return true;
            }
            return false;
        }
        
        public Material getRandomOre() {
            return ores[new Random().nextInt(ores.length)];
        }
        
        public void resetBlocks() {
            // Implementation would reset all blocks in the mine region
        }
    }
}
