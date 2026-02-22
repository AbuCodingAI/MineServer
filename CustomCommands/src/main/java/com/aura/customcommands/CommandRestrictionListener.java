package com.aura.customcommands;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class CommandRestrictionListener implements Listener {

    private Set<String> playerCommands;

    public CommandRestrictionListener(Plugin plugin) {
        loadPlayerCommands(plugin);
    }

    private void loadPlayerCommands(Plugin plugin) {
        playerCommands = new HashSet<>();
        try {
            InputStream inputStream = plugin.getResource("player-commands.json");
            if (inputStream != null) {
                InputStreamReader reader = new InputStreamReader(inputStream);
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(reader, JsonObject.class);
                JsonArray commands = json.getAsJsonArray("playerCommands");
                for (int i = 0; i < commands.size(); i++) {
                    playerCommands.add(commands.get(i).getAsString().toLowerCase());
                }
                reader.close();
                plugin.getLogger().info("Loaded " + playerCommands.size() + " player commands from JSON");
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to load player-commands.json: " + e.getMessage());
            // Fallback to default commands
            playerCommands.add("home");
            playerCommands.add("sethome");
            playerCommands.add("lobby");
            playerCommands.add("rtp");
            playerCommands.add("warp");
            playerCommands.add("tp");
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().toLowerCase();

        if (!command.startsWith("/")) {
            return;
        }

        String commandName = command.substring(1).split(" ")[0];

        // Block owner-only commands for non-owners
        if ((commandName.equals("player") || commandName.equals("unplayer") || commandName.equals("admin") || 
             commandName.equals("nocheats")) && !PermissionManager.isOwner(player)) {
            event.setCancelled(true);
            player.sendMessage("§c[Server] This command is owner-only!");
            return;
        }

        // Allow owners and admins to use all other commands
        if (PermissionManager.isOwner(player) || PermissionManager.isAdmin(player)) {
            return;
        }

        // Check if command is in the allowed player commands list
        if (playerCommands.contains(commandName)) {
            return;
        }

        // Block all other commands for regular players
        event.setCancelled(true);
        player.sendMessage("§c[Server] You don't have permission to use this command!");
        player.sendMessage("§7Available commands: " + String.join(", /", playerCommands));
    }
}
