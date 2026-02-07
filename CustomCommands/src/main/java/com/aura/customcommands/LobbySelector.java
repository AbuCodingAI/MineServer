package com.aura.customcommands;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class LobbySelector implements Listener {

    public LobbySelector(JavaPlugin plugin) {
    }

    public ItemStack createLobbySelectorItem() {
        ItemStack item = new ItemStack(Material.BEDROCK);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§b§lLobby Selector");
            meta.setLore(Arrays.asList(
                    "§7Right-click to open lobby menu",
                    "§7Choose your gamemode!"
            ));
            item.setItemMeta(meta);
        }
        return item;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.BEDROCK) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName() || !meta.getDisplayName().contains("Lobby Selector")) {
            return;
        }

        event.setCancelled(true);
        Player player = event.getPlayer();
        openLobbyMenu(player);
    }

    private void openLobbyMenu(Player player) {
        player.sendMessage("§b§m" + "=".repeat(50));
        player.sendMessage("§b§lLobby Selector");
        player.sendMessage("§b§m" + "=".repeat(50));
        player.sendMessage("§7Click on a lobby to join:");
        player.sendMessage("");
        player.sendMessage("§a/lobby skyblock §7- Skyblock gamemode");
        player.sendMessage("§a/lobby lifesteal §7- Lifesteal gamemode");
        player.sendMessage("§a/lobby pvp §7- PvP gamemode");
        player.sendMessage("§a/lobby survival §7- Pure Survival (Vanilla)");
        player.sendMessage("§a/lobby manhunt §7- Manhunt gamemode");
        player.sendMessage("");
        player.sendMessage("§b§m" + "=".repeat(50));
    }
}
