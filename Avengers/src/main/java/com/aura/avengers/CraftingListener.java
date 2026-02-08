package com.aura.avengers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftingListener {

    public static void registerRecipes(JavaPlugin plugin) {
        // Mjolnir Recipe: Mace + Trident (with Channeling and Loyalty)
        ItemStack mjolnir = createMjolnir();
        NamespacedKey mjolnirKey = new NamespacedKey(plugin, "mjolnir");
        ShapedRecipe mjolnirRecipe = new ShapedRecipe(mjolnirKey, mjolnir);
        
        mjolnirRecipe.shape(
            "MT"
        );
        mjolnirRecipe.setIngredient('M', Material.MACE);
        mjolnirRecipe.setIngredient('T', Material.TRIDENT);
        
        Bukkit.addRecipe(mjolnirRecipe);

        // Iron Man Gloves Recipe: 8 iron ingots + 1 beacon in center
        ItemStack gloves = createIronManGloves();
        NamespacedKey glovesKey = new NamespacedKey(plugin, "iron_man_gloves");
        ShapedRecipe glovesRecipe = new ShapedRecipe(glovesKey, gloves);
        
        glovesRecipe.shape(
            "III",
            "IBI",
            "III"
        );
        glovesRecipe.setIngredient('I', Material.IRON_INGOT);
        glovesRecipe.setIngredient('B', Material.BEACON);
        
        Bukkit.addRecipe(glovesRecipe);

        // Vibranium Recipe: 9 iron blocks → 1 vibranium
        ItemStack vibranium = createVibranium();
        NamespacedKey vibraniumKey = new NamespacedKey(plugin, "vibranium");
        ShapedRecipe vibraniumRecipe = new ShapedRecipe(vibraniumKey, vibranium);
        
        vibraniumRecipe.shape(
            "III",
            "III",
            "III"
        );
        vibraniumRecipe.setIngredient('I', Material.IRON_BLOCK);
        
        Bukkit.addRecipe(vibraniumRecipe);

        // Captain America Shield Recipe: 9 vibranium + 1 shield in center
        ItemStack shield = createCaptainShield();
        NamespacedKey shieldKey = new NamespacedKey(plugin, "captain_shield");
        ShapedRecipe shieldRecipe = new ShapedRecipe(shieldKey, shield);
        
        shieldRecipe.shape(
            "VVV",
            "VSV",
            "VVV"
        );
        shieldRecipe.setIngredient('V', Material.LAPIS_BLOCK);
        shieldRecipe.setIngredient('S', Material.SHIELD);
        
        Bukkit.addRecipe(shieldRecipe);
    }

    private static ItemStack createVibranium() {
        ItemStack vibranium = new ItemStack(Material.LAPIS_BLOCK);
        ItemMeta meta = vibranium.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§9§lVibranium");
            meta.setLore(java.util.Arrays.asList(
                    "§7The legendary metal",
                    "§7Used to craft Captain America Shield"
            ));
            vibranium.setItemMeta(meta);
        }
        return vibranium;
    }

    private static ItemStack createMjolnir() {
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
        mjolnir.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.BREACH, 4);
        mjolnir.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.WIND_BURST, 3);
        mjolnir.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DENSITY, 5);
        mjolnir.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.CHANNELING, 1);
        mjolnir.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.LOYALTY, 3);
        return mjolnir;
    }

    private static ItemStack createIronManGloves() {
        ItemStack gloves = new ItemStack(Material.NETHERITE_BOOTS);
        ItemMeta meta = gloves.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§c§lIron Man Gloves");
            meta.setLore(java.util.Arrays.asList(
                    "§7Right-click to shoot beam",
                    "§7Deals 5 hearts damage",
                    "§7Blocks shields"
            ));
            gloves.setItemMeta(meta);
        }
        gloves.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION, 4);
        gloves.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.UNBREAKING, 3);
        gloves.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.MENDING, 1);
        return gloves;
    }

    private static ItemStack createCaptainShield() {
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
        shield.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.LOYALTY, 3);
        shield.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.UNBREAKING, 3);
        return shield;
    }
}
