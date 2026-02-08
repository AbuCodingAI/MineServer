package com.aura.avengers;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public 
  class
 AvengersCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        switch (command.getName().toLowerCase()) {
            case "mjolnir":
                giveMjolnir(player);
                return true;
            case "ironman":
                giveIronManArmor(player);
                return true;
            case "captainshield":
                giveCaptainShield(player);
                return true;
            case "hawkeyearrows":
                giveHawkeyeArrows(player);
                return true;
        }

        return false;
    }

    private void giveMjolnir(Player player) {
        ItemStack mjolnir = new ItemStack(Material.MACE);
        ItemMeta meta = mjolnir.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6§lMjolnir");
            meta.setLore(java.util.Arrays.asList(
                    "§7The mighty hammer of Thor",
                    "§7Breach, Windblast, Density",
                    "§7Summons lightning on hit",
                    "§7Always returns to wielder"
            ));
            mjolnir.setItemMeta(meta);
        }
        mjolnir.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.BREACH, 255);
        mjolnir.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.WIND_BURST, 255);
        mjolnir.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DENSITY, 255);
        mjolnir.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.CHANNELING, 255);
        mjolnir.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.LOYALTY, 255);

        player.getInventory().addItem(mjolnir);
        player.sendMessage("§6[Avengers] You have received Mjolnir!");
    }

    private void giveIronManArmor(Player player) {
        ItemStack helmet = new ItemStack(Material.NETHERITE_HELMET);
        ItemStack chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.NETHERITE_LEGGINGS);
        ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS);

        addIronManEnchantments(helmet);
        addIronManEnchantments(chestplate);
        addIronManEnchantments(leggings);
        addIronManEnchantments(boots);

        ItemMeta helmetMeta = helmet.getItemMeta();
        if (helmetMeta != null) {
            helmetMeta.setDisplayName("§c§lIron Man Helmet");
            helmet.setItemMeta(helmetMeta);
        }

        ItemMeta chestMeta = chestplate.getItemMeta();
        if (chestMeta != null) {
            chestMeta.setDisplayName("§c§lIron Man Chestplate");
            chestplate.setItemMeta(chestMeta);
        }

        ItemStack gloves = new ItemStack(Material.NETHERITE_BOOTS);
        ItemMeta glovesMeta = gloves.getItemMeta();
        if (glovesMeta != null) {
            glovesMeta.setDisplayName("§c§lIron Man Gloves");
            glovesMeta.setLore(java.util.Arrays.asList(
                    "§7Right-click to shoot beam",
                    "§7Deals 5 hearts damage",
                    "§7Blocks shields"
            ));
            gloves.setItemMeta(glovesMeta);
        }
        addIronManEnchantments(gloves);

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        player.getInventory().addItem(gloves);

        player.sendMessage("§c[Avengers] You have received Iron Man armor!");
    }

    private void addIronManEnchantments(ItemStack item) {
        item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION, 255);
        item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.UNBREAKING, 255);
        item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.MENDING, 255);
    }

    private void giveCaptainShield(Player player) {
        ItemStack shield = new ItemStack(Material.SHIELD);
        ItemMeta meta = shield.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§9§lCaptain America Shield");
            meta.setLore(java.util.Arrays.asList(
                    "§7The legendary vibranium shield",
                    "§7Right-click to throw",
                    "§7Deals 4 hearts damage",
                    "§7Returns with Loyalty"
            ));
            shield.setItemMeta(meta);
        }
        shield.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.LOYALTY, 255);
        shield.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.UNBREAKING, 255);

        player.getInventory().addItem(shield);
        player.sendMessage("§9[Avengers] You have received Captain America Shield!");
    }

    private void giveHawkeyeArrows(Player player) {
        for (int i = 0; i < 64; i++) {
            ItemStack arrow = new ItemStack(Material.ARROW);
            ItemMeta meta = arrow.getItemMeta();
            if (meta != null) {
                meta.setDisplayName("§a§lHawkeye Arrow");
                meta.setLore(java.util.Arrays.asList(
                        "§7Auto-aims at closest enemy",
                        "§7Never misses"
                ));
                arrow.setItemMeta(meta);
            }
            player.getInventory().addItem(arrow);
        }

        player.sendMessage("§a[Avengers] You have received 64 Hawkeye arrows!");
    }
}
