package com.aura.avengers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class MjolnirListener implements Listener {

    private Map<UUID, Long> chargeStartTime = new HashMap<>();
    private Map<UUID, BukkitRunnable> chargeTask = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Check if player is holding Mjolnir
        if (item != null && item.getType() == Material.MACE
                && item.getItemMeta() != null && item.getItemMeta().hasDisplayName()
                && item.getItemMeta().getDisplayName().contains("Mjolnir")) {

            // Right click to throw
            if (event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR
                    || event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {

                event.setCancelled(true);

                // Start charging if not already charging
                if (!chargeStartTime.containsKey(player.getUniqueId())) {
                    chargeStartTime.put(player.getUniqueId(), System.currentTimeMillis());
                    player.sendMessage("§6[Mjolnir] Charging throw... (3 seconds)");

                    // Schedule throw after 3 seconds
                    BukkitRunnable task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (chargeStartTime.containsKey(player.getUniqueId())) {
                                throwMjolnir(player, item);
                                chargeStartTime.remove(player.getUniqueId());
                                chargeTask.remove(player.getUniqueId());
                            }
                        }
                    };
                    task.runTaskLater(AvengersPlugin.getInstance(), 60); // 3 seconds = 60 ticks
                    chargeTask.put(player.getUniqueId(), task);
                }
            }
        } else if ((event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR
                || event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)
                && (item == null || item.getType() == Material.AIR)) {

            // Player doesn't have Mjolnir, try to summon it
            if (!chargeStartTime.containsKey(player.getUniqueId())) {
                chargeStartTime.put(player.getUniqueId(), System.currentTimeMillis());
                player.sendMessage("§6[Mjolnir] Summoning... Hold right click for 3 seconds");

                // Schedule summon after 3 seconds
                BukkitRunnable task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (chargeStartTime.containsKey(player.getUniqueId())) {
                            summonMjolnir(player);
                            chargeStartTime.remove(player.getUniqueId());
                            chargeTask.remove(player.getUniqueId());
                        }
                    }
                };
                task.runTaskLater(AvengersPlugin.getInstance(), 60);
                chargeTask.put(player.getUniqueId(), task);
            }
        }
    }

    @EventHandler
    public void onPlayerLeftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Cancel charge on left click
        if (event.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_AIR
                || event.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK) {

            UUID uuid = player.getUniqueId();
            if (chargeStartTime.containsKey(uuid)) {
                chargeStartTime.remove(uuid);
                if (chargeTask.containsKey(uuid)) {
                    chargeTask.get(uuid).cancel();
                    chargeTask.remove(uuid);
                }
                player.sendMessage("§c[Mjolnir] Charge cancelled!");
            }
        }
    }

    private void summonMjolnir(Player player) {
        ItemStack mjolnir = new ItemStack(Material.MACE);
        org.bukkit.inventory.meta.ItemMeta meta = mjolnir.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6§lMjolnir - " + player.getName());
            meta.setLore(java.util.Arrays.asList(
                    "§7Owner: " + player.getName(),
                    "§7Right-click to throw (charge 3s)",
                    "§7Deals 30 hearts damage"
            ));
            mjolnir.setItemMeta(meta);
        }
        mjolnir.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.BREACH, 1);
        mjolnir.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.WIND_BURST, 3);
        mjolnir.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DENSITY, 5);
        mjolnir.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.CHANNELING, 1);
        mjolnir.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.LOYALTY, 3);

        player.getInventory().addItem(mjolnir);
        player.sendMessage("§6[Mjolnir] Mjolnir has been summoned! It is now yours!");
    }

    private void throwMjolnir(Player player, ItemStack mjolnir) {
        // Check if player owns this Mjolnir
        if (mjolnir.getItemMeta() == null || !mjolnir.getItemMeta().getDisplayName().contains(player.getName())) {
            player.sendMessage("§c[Mjolnir] You don't own this Mjolnir!");
            return;
        }

        // Remove from inventory
        player.getInventory().remove(mjolnir);

        // Create projectile using armor stand
        org.bukkit.entity.ArmorStand stand = player.getWorld().spawn(
                player.getEyeLocation(),
                org.bukkit.entity.ArmorStand.class
        );
        stand.setVisible(false);
        stand.setGravity(true);
        stand.getEquipment().setHelmet(mjolnir);

        // Throw in direction player is looking
        Vector direction = player.getLocation().getDirection().normalize().multiply(2);
        stand.setVelocity(direction);

        player.sendMessage("§6[Mjolnir] Mjolnir thrown!");

        // Track for damage and return
        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                ticks++;

                // Check for entity hits
                for (LivingEntity entity : stand.getWorld().getLivingEntities()) {
                    if (entity == player || entity == stand) {
                        continue;
                    }

                    double distance = stand.getLocation().distance(entity.getLocation());
                    if (distance < 1.5) {
                        // Deal 30 hearts damage (60 damage)
                        entity.damage(60, player);
                        player.sendMessage("§6[Mjolnir] Hit! Returning...");
                        stand.remove();
                        this.cancel();
                        return;
                    }
                }

                // Return after 10 seconds or if too far
                if (ticks > 200 || stand.getLocation().distance(player.getLocation()) > 100) {
                    stand.remove();
                    player.getInventory().addItem(mjolnir);
                    player.sendMessage("§6[Mjolnir] Mjolnir has returned!");
                    this.cancel();
                }
            }
        }.runTaskTimer(AvengersPlugin.getInstance(), 0, 1);
    }

    @EventHandler
    public void onMjolnirHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() != Material.MACE) {
            return;
        }

        if (item.getItemMeta() == null || !item.getItemMeta().hasDisplayName()) {
            return;
        }

        if (!item.getItemMeta().getDisplayName().contains("Mjolnir")) {
            return;
        }

        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity target = (LivingEntity) event.getEntity();

            // Always summon lightning regardless of weather
            player.getWorld().strikeLightning(target.getLocation());

            // Extra damage
            event.setDamage(event.getDamage() * 2);
        }
    }
}
