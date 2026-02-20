package com.aura.customcommands;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandRestrictionListener implements Listener {

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().toLowerCase();

        // Allow owners and admins to use all commands
        if (PermissionManager.isOwner(player) || PermissionManager.isAdmin(player)) {
            return;
        }

        // Allow /home and /sethome for everyone
        if (command.startsWith("/home") || command.startsWith("/sethome")) {
            return;
        }

        // Allow /lobby for everyone
        if (command.startsWith("/lobby")) {
            return;
        }

        // Block all other commands for regular players
        if (command.startsWith("/")) {
            event.setCancelled(true);
            player.sendMessage("§c[Server] You don't have permission to use this command!");
            player.sendMessage("§7Only admins and owners can use commands.");
        }
    }
}
