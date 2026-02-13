package com.aura.avengers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InfinityGauntletListener implements Listener {

    private Map<UUID, Long> gauntletCooldown = new HashMap<>();

    @EventHandler
    public void onGauntletPlace(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() != Material.GOLD_BLOCK) {
            return;
        }

        if (item.getItemMeta() == null || !item.getItemMeta().hasDisplayName()) {
            return;
        }

        if (!item.getItemMeta().getDisplayName().contains("Infinity Gauntlet")) {
            return;
        }

        event.setCancelled(true);

        // Check if player has all 6 infinity stones
        if (!hasAllInfinityStones(player)) {
            player.sendMessage("§c[Infinity Gauntlet] You need all 6 Infinity Stones!");
            return;
        }

        // Check cooldown (3 hearts damage + 6 second immunity)
        UUID uuid = player.getUniqueId();
        long currentTime = System.currentTimeMillis();
        if (gauntletCooldown.containsKey(uuid)) {
            long lastUse = gauntletCooldown.get(uuid);
            if (currentTime - lastUse < 6000) { // 6 seconds
                player.sendMessage("§c[Infinity Gauntlet] Still recovering from last use!");
                return;
            }
        }

        // Open creative menu (simulated with inventory)
        openCreativeMenu(player);
        gauntletCooldown.put(uuid, currentTime);

        // Deal 3 hearts damage
        player.damage(6);

        // Apply 6 second immunity
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 120, 255, false, false));

        player.sendMessage("§d[Infinity Gauntlet] Reality bends to your will!");
    }

    private boolean hasAllInfinityStones(Player player) {
        int stonesFound = 0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;

            if (item.getItemMeta() == null || !item.getItemMeta().hasDisplayName()) {
                continue;
            }

            String displayName = item.getItemMeta().getDisplayName();
            if (displayName.contains("Power Stone") ||
                displayName.contains("Reality Stone") ||
                displayName.contains("Time Stone") ||
                displayName.contains("Mind Stone") ||
                displayName.contains("Soul Stone") ||
                displayName.contains("Space Stone")) {
                stonesFound++;
            }
        }

        return stonesFound >= 6;
    }

    private void openCreativeMenu(Player player) {
        // Open player's inventory as a creative-like interface
        // In a real implementation, you'd use a custom GUI library
        // For now, we'll give them a bundle to fill with items
        ItemStack bundle = new ItemStack(Material.BUNDLE);
        ItemMeta meta = bundle.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§d§lInfinity Bundle");
            meta.setLore(java.util.Arrays.asList(
                    "§7Fill this bundle with any items",
                    "§7You can carry up to 64 of any item"
            ));
            bundle.setItemMeta(meta);
        }

        player.getInventory().addItem(bundle);
        player.sendMessage("§d[Infinity Gauntlet] An Infinity Bundle has been created!");
    }
}
