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

        // Allow owners and admins to use all commands
        if (PermissionManager.isOwner(player) || PermissionManager.isAdmin(player)) {
            return;
        }

        // Extract the command name (first word after /)
        String commandName = command.substring(1).split(" ")[0];

        // Allow all claim-related commands (GriefPrevention)
        if (commandName.equals("claim") || commandName.equals("acb") || commandName.equals("abandonclaim") ||
            commandName.equals("claimlist") || commandName.equals("subclaim") || commandName.equals("trust") ||
            commandName.equals("untrust") || commandName.equals("accesstrust") || commandName.equals("permissiontrust") ||
            commandName.equals("trustlist") || commandName.equals("untrustall")) {
            return;
        }

        // Block QA (QualityArmory) commands for regular players
        if (commandName.startsWith("qa") || commandName.equals("gun") || commandName.equals("guns") || 
            commandName.equals("ammo") || commandName.equals("gunshop")) {
            event.setCancelled(true);
            player.sendMessage("§c[Server] QualityArmory commands are only available to admins and owners!");
            return;
        }

        // Check if command is in the allowed player commands list
        if (playerCommands.contains(commandName)) {
            return;
        }

        // Block all other commands for regular players
        if (command.startsWith("/")) {
            event.setCancelled(true);
            player.sendMessage("§c[Server] You don't have permission to use this command!");
            player.sendMessage("§7Available commands: /home, /sethome, /warp, /claim, /acb, and claim-related commands");
        }
    }
}
