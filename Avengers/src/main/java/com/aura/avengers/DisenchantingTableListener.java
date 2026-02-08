package com.aura.avengers;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class DisenchantingTableListener implements Listener {

    @EventHandler
    public void onDisenchantingTableUse(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.ENCHANTING_TABLE) {
            return;
        }

        // Check if this is a disenchanting table (check nearby blocks)
        if (!isDisenchantingTable(block)) {
            return;
        }

        event.setCancelled(true);
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage("§c[Disenchanting] Hold an enchanted item!");
            return;
        }

        if (item.getItemMeta() == null || !item.getItemMeta().hasEnchants()) {
            player.sendMessage("§c[Disenchanting] Item has no enchantments!");
            return;
        }

        disenchantItem(player, item);
    }

    private boolean isDisenchantingTable(Block block) {
        // Check if surrounded by the disenchanting table pattern
        // Lapis on top, obsidian on bottom, book in middle
        Block above = block.getRelative(0, 1, 0);
        Block below = block.getRelative(0, -1, 0);

        return above.getType() == Material.LAPIS_BLOCK && below.getType() == Material.OBSIDIAN;
    }

    private void disenchantItem(Player player, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }

        Map<Enchantment, Integer> enchantments = meta.getEnchants();

        if (enchantments.isEmpty()) {
            player.sendMessage("§c[Disenchanting] Item has no enchantments!");
            return;
        }

        // Create enchanted books for each enchantment
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();

            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) book.getItemMeta();
            if (bookMeta != null) {
                bookMeta.addStoredEnchant(enchantment, level, true);
                bookMeta.setDisplayName("§b§l" + enchantment.getKey().getKey());
                book.setItemMeta(bookMeta);
            }

            player.getInventory().addItem(book);
        }

        // Remove all enchantments from the item
        ItemMeta newMeta = item.getItemMeta();
        if (newMeta != null) {
            for (Enchantment enchantment : enchantments.keySet()) {
                newMeta.removeEnchant(enchantment);
            }
            item.setItemMeta(newMeta);
        }

        player.sendMessage("§a[Disenchanting] Item disenchanted! Books added to inventory.");
    }
}
