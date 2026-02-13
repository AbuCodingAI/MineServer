package com.aura.avengers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
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

        // Power Stone Recipe: 18 vibranium blocks
        ItemStack powerStone = createPowerStone();
        NamespacedKey powerStoneKey = new NamespacedKey(plugin, "power_stone");
        ShapedRecipe powerStoneRecipe = new ShapedRecipe(powerStoneKey, powerStone);

        powerStoneRecipe.shape(
                "VVV",
                "VVV",
                "VVV"
        );
        powerStoneRecipe.setIngredient('V', Material.LAPIS_BLOCK);

        Bukkit.addRecipe(powerStoneRecipe);

        // Reality Stone Recipe: 9 netherite blocks
        ItemStack realityStone = createRealityStone();
        NamespacedKey realityStoneKey = new NamespacedKey(plugin, "reality_stone");
        ShapedRecipe realityStoneRecipe = new ShapedRecipe(realityStoneKey, realityStone);

        realityStoneRecipe.shape(
                "NNN",
                "NNN",
                "NNN"
        );
        realityStoneRecipe.setIngredient('N', Material.NETHERITE_BLOCK);

        Bukkit.addRecipe(realityStoneRecipe);

        // Time Stone Recipe: Clock center with 3 amethyst top and 5 obsidian
        ItemStack timeStone = createTimeStone();
        NamespacedKey timeStoneKey = new NamespacedKey(plugin, "time_stone");
        ShapedRecipe timeStoneRecipe = new ShapedRecipe(timeStoneKey, timeStone);

        timeStoneRecipe.shape(
                "AAA",
                "OCO",
                "OOO"
        );
        timeStoneRecipe.setIngredient('A', Material.AMETHYST_BLOCK);
        timeStoneRecipe.setIngredient('O', Material.OBSIDIAN);
        timeStoneRecipe.setIngredient('C', Material.CLOCK);

        Bukkit.addRecipe(timeStoneRecipe);

        // Mind Stone Recipe: Netherite helmet center, dragon egg top, pumpkin bottom, 2 eyes of ender sides
        ItemStack mindStone = createMindStone();
        NamespacedKey mindStoneKey = new NamespacedKey(plugin, "mind_stone");
        ShapedRecipe mindStoneRecipe = new ShapedRecipe(mindStoneKey, mindStone);

        mindStoneRecipe.shape(
                "EDE",
                "HNH",
                "EPE"
        );
        mindStoneRecipe.setIngredient('E', Material.ENDER_EYE);
        mindStoneRecipe.setIngredient('D', Material.DRAGON_EGG);
        mindStoneRecipe.setIngredient('H', Material.NETHERITE_HELMET);
        mindStoneRecipe.setIngredient('P', Material.PUMPKIN);

        Bukkit.addRecipe(mindStoneRecipe);

        // Soul Stone Recipe: 3 soul sand top, soul sand middle, 3 obsidian bottom
        ItemStack soulStone = createSoulStone();
        NamespacedKey soulStoneKey = new NamespacedKey(plugin, "soul_stone");
        ShapedRecipe soulStoneRecipe = new ShapedRecipe(soulStoneKey, soulStone);

        soulStoneRecipe.shape(
                "SSS",
                "SBS",
                "OOO"
        );
        soulStoneRecipe.setIngredient('S', Material.SOUL_SAND);
        soulStoneRecipe.setIngredient('B', Material.SOUL_SAND);
        soulStoneRecipe.setIngredient('O', Material.OBSIDIAN);

        Bukkit.addRecipe(soulStoneRecipe);

        // Space Stone Recipe: 3 end rods top, vibranium center, 6 netherite ingots bottom
        ItemStack spaceStone = createSpaceStone();
        NamespacedKey spaceStoneKey = new NamespacedKey(plugin, "space_stone");
        ShapedRecipe spaceStoneRecipe = new ShapedRecipe(spaceStoneKey, spaceStone);

        spaceStoneRecipe.shape(
                "RRR",
                "NVN",
                "NNN"
        );
        spaceStoneRecipe.setIngredient('R', Material.END_ROD);
        spaceStoneRecipe.setIngredient('V', Material.LAPIS_BLOCK);
        spaceStoneRecipe.setIngredient('N', Material.NETHERITE_INGOT);

        Bukkit.addRecipe(spaceStoneRecipe);

        // Infinity Gauntlet Recipe: 3 gold blocks
        ItemStack gauntlet = createInfinityGauntlet();
        NamespacedKey gauntletKey = new NamespacedKey(plugin, "infinity_gauntlet");
        ShapedRecipe gauntletRecipe = new ShapedRecipe(gauntletKey, gauntlet);

        gauntletRecipe.shape(
                "GGG"
        );
        gauntletRecipe.setIngredient('G', Material.GOLD_BLOCK);

        Bukkit.addRecipe(gauntletRecipe);
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

    public static void registerDisenchantingTableRecipe(JavaPlugin plugin) {
        // Disenchanting Table Recipe: Lapis on top, Obsidian on bottom, Diamonds and Book in middle
        ItemStack disenchantingTable = new ItemStack(Material.ENCHANTING_TABLE);
        ItemMeta tableMeta = disenchantingTable.getItemMeta();
        if (tableMeta != null) {
            tableMeta.setDisplayName("§5§lDisenchanting Table");
            disenchantingTable.setItemMeta(tableMeta);
        }
        NamespacedKey tableKey = new NamespacedKey(plugin, "disenchanting_table");
        ShapedRecipe tableRecipe = new ShapedRecipe(tableKey, disenchantingTable);

        tableRecipe.shape(
                "LLL",
                "DBD",
                "OOO"
        );
        tableRecipe.setIngredient('L', Material.LAPIS_LAZULI);
        tableRecipe.setIngredient('D', Material.DIAMOND);
        tableRecipe.setIngredient('B', Material.BOOK);
        tableRecipe.setIngredient('O', Material.OBSIDIAN);

        Bukkit.addRecipe(tableRecipe);
    }

    private static ItemStack createPowerStone() {
        ItemStack stone = new ItemStack(Material.AMETHYST_BLOCK);
        ItemMeta meta = stone.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§d§lPower Stone");
            meta.setLore(java.util.Arrays.asList(
                    "§7One of the Infinity Stones",
                    "§7Grants immense power"
            ));
            stone.setItemMeta(meta);
        }
        return stone;
    }

    private static ItemStack createRealityStone() {
        ItemStack stone = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = stone.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§c§lReality Stone");
            meta.setLore(java.util.Arrays.asList(
                    "§7One of the Infinity Stones",
                    "§7Bends reality itself"
            ));
            stone.setItemMeta(meta);
        }
        return stone;
    }

    private static ItemStack createTimeStone() {
        ItemStack stone = new ItemStack(Material.CLOCK);
        ItemMeta meta = stone.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§e§lTime Stone");
            meta.setLore(java.util.Arrays.asList(
                    "§7One of the Infinity Stones",
                    "§7Controls the flow of time"
            ));
            stone.setItemMeta(meta);
        }
        return stone;
    }

    private static ItemStack createMindStone() {
        ItemStack stone = new ItemStack(Material.YELLOW_CONCRETE);
        ItemMeta meta = stone.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§e§lMind Stone");
            meta.setLore(java.util.Arrays.asList(
                    "§7One of the Infinity Stones",
                    "§7Grants telepathic powers"
            ));
            stone.setItemMeta(meta);
        }
        return stone;
    }

    private static ItemStack createSoulStone() {
        ItemStack stone = new ItemStack(Material.ORANGE_CONCRETE);
        ItemMeta meta = stone.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6§lSoul Stone");
            meta.setLore(java.util.Arrays.asList(
                    "§7One of the Infinity Stones",
                    "§7Governs the soul"
            ));
            stone.setItemMeta(meta);
        }
        return stone;
    }

    private static ItemStack createSpaceStone() {
        ItemStack stone = new ItemStack(Material.PURPLE_CONCRETE);
        ItemMeta meta = stone.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§5§lSpace Stone");
            meta.setLore(java.util.Arrays.asList(
                    "§7One of the Infinity Stones",
                    "§7Controls space and dimensions"
            ));
            stone.setItemMeta(meta);
        }
        return stone;
    }

    private static ItemStack createInfinityGauntlet() {
        ItemStack gauntlet = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta meta = gauntlet.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§d§lInfinity Gauntlet");
            meta.setLore(java.util.Arrays.asList(
                    "§7The ultimate weapon",
                    "§7Right-click with all 6 stones",
                    "§7Opens creative menu",
                    "§7Costs 3 hearts + 6s cooldown"
            ));
            gauntlet.setItemMeta(meta);
        }
        return gauntlet;
    }
}
