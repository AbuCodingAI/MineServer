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
        gloves.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION, 255);
        gloves.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.UNBREAKING, 255);
        gloves.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.MENDING, 255);
        return gloves;
    }
}
