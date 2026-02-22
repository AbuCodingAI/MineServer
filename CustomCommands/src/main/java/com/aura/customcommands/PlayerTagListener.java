package com.aura.customcommands;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerTagListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Add owner tag
        if (PermissionManager.isOwner(player)) {
            player.setDisplayName("§c§l[OWNER] §r" + player.getName());
            player.setPlayerListName("§c§l[OWNER] §r" + player.getName());
        }
        // Add admin tag
        else if (PermissionManager.isAdmin(player)) {
            player.setDisplayName("§9§l[ADMIN] §r" + player.getName());
            player.setPlayerListName("§9§l[ADMIN] §r" + player.getName());
        }
    }
}
