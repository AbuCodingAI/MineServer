package com.aura.customcommands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ManhuntGame {
    
    public enum WinCondition {
        HOW_DID_WE_GET_HERE("How Did We Get Here", "Speedrunner wins by defeating the Ender Dragon"),
        TRADITIONAL("Traditional", "Speedrunner wins by reaching the End and defeating the dragon");
        
        private final String displayName;
        private final String description;
        
        WinCondition(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    public enum HunterWinCondition {
        BEAT_DRAGON("Hunters beat Ender Dragon", "Hunters win if they defeat the dragon before speedrunner"),
        KILL_SPEEDRUNNER("Kill Speedrunner", "Hunters win if they kill the speedrunner");
        
        private final String displayName;
        private final String description;
        
        HunterWinCondition(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    private static final Map<String, ManhuntRewards> REWARDS = new HashMap<>();
    
    static {
        REWARDS.put("eyes_of_ender", new ManhuntRewards(Material.ENDER_EYE, 10));
        REWARDS.put("wither_skeletons", new ManhuntRewards(Material.WITHER_SKELETON_SKULL, 12));
        REWARDS.put("ender_pearls", new ManhuntRewards(Material.ENDER_PEARL, 16));
        REWARDS.put("bundles", new ManhuntRewards(Material.BUNDLE, 10));
        REWARDS.put("hearts_of_sea", new ManhuntRewards(Material.HEART_OF_THE_SEA, 5));
        REWARDS.put("beacons", new ManhuntRewards(Material.BEACON, 3));
    }
    
    public static void giveHunterRewards(Player player) {
        giveRewards(player, "Hunter");
    }
    
    public static void giveSpeedrunnerRewards(Player player) {
        giveRewards(player, "Speedrunner");
    }
    
    private static void giveRewards(Player player, String role) {
        for (ManhuntRewards reward : REWARDS.values()) {
            ItemStack item = new ItemStack(reward.getMaterial(), reward.getAmount());
            player.getInventory().addItem(item);
        }
        player.sendMessage("§a[Manhunt] " + role + " rewards given!");
    }
    
    public static class ManhuntRewards {
        private final Material material;
        private final int amount;
        
        public ManhuntRewards(Material material, int amount) {
            this.material = material;
            this.amount = amount;
        }
        
        public Material getMaterial() {
            return material;
        }
        
        public int getAmount() {
            return amount;
        }
    }
}
