package com.aura.customcommands;

import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class PermissionManager {
    
    private static final String[] OWNERS = {"IamAura67", "I_am_Aura_67"};
    private static final List<String> ADMINS = new ArrayList<>(); // Dynamic admin list
    
    public enum Role {
        OWNER(3),
        ADMIN(2),
        USER(1);
        
        private final int level;
        
        Role(int level) {
            this.level = level;
        }
        
        public int getLevel() {
            return level;
        }
    }
    
    public static Role getPlayerRole(Player player) {
        String name = player.getName();
        
        // Check if owner
        for (String owner : OWNERS) {
            if (name.equals(owner)) {
                return Role.OWNER;
            }
        }
        
        // Check if admin
        if (ADMINS.contains(name)) {
            return Role.ADMIN;
        }
        
        if (player.hasPermission("customcommands.admin")) {
            return Role.ADMIN;
        }
        
        return Role.USER;
    }
    
    public static boolean hasPermission(Player player, Role requiredRole) {
        Role playerRole = getPlayerRole(player);
        return playerRole.getLevel() >= requiredRole.getLevel();
    }
    
    public static boolean isOwner(Player player) {
        String name = player.getName();
        for (String owner : OWNERS) {
            if (name.equals(owner)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAdmin(Player player) {
        return hasPermission(player, Role.ADMIN);
    }
    
    public static void addAdmin(String username) {
        if (!ADMINS.contains(username)) {
            ADMINS.add(username);
        }
    }
}
