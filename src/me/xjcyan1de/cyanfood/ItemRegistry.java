package me.xjcyan1de.cyanfood;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import static me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem.getItem;

public class ItemRegistry {
    private final Main main;
    private final Configuration config;

    public ItemRegistry(Main main) {
        this.main = main;
        this.config = main.config;
        main.category_plants = new Category(new CustomItem(getSkull(Material.NETHER_STALK, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTVhNWM0YTBhMTZkYWJjOWIxZWM3MmZjODNlMjNhYzE1ZDAxOTdkZTYxYjEzOGJhYmNhN2M4YTI5YzgyMCJ9fX0="), "§7Растения и фрукты", "", "§a> Кликни, чтобы открыть"));
        registerItems();
    }

    private void registerItems() {
        registerBerry("GRAPE", PlantType.BUSH);
        registerBerry("BLUEBERRY", PlantType.BUSH);
        registerBerry("ELDERBERRY", PlantType.BUSH);
        registerBerry("RASPBERRY", PlantType.BUSH);
        registerBerry("BLACKBERRY", PlantType.BUSH);
    }

    private ItemStack getSkull(MaterialData material, String texture) {
        try {
            if (texture.equals("NO_SKULL_SPECIFIED")) return material.toItemStack(1);
            return CustomSkull.getItem(texture);
        } catch (Exception ex) {
            ex.printStackTrace();
            return material.toItemStack(1);
        }
    }

    private ItemStack getSkull(Material material, String texture) {
        return getSkull(new MaterialData(material), texture);
    }

    private void registerBerry(String id, PlantType type) {
        String berryname = config.getString("items."+id.toLowerCase()+".berryname");
        String bushname = config.getString("items."+id.toLowerCase()+".bushname");
        PlantData data = new PlantData(config.getString("items."+id.toLowerCase()+".texture"));
        Berry berry = new Berry(id, type, data);
        main.saplings.add(new CustomItem(Material.SAPLING, bushname, 0));
        main.berries.add(berry);

        new SlimefunItem(main.category_plants, new CustomItem(Material.SAPLING, bushname, 0), id + "_BUSH", new RecipeType(new CustomItem(Material.LONG_GRASS, config.getString("guide.recipe.bush"), 1)),
                new ItemStack[]{null, null, null, null, new CustomItem(Material.LONG_GRASS, 1), null, null, null, null})
                .register();

        new Plant(main.category_plants, new CustomItem(getSkull(Material.NETHER_STALK, data.getTexture()), berryname), id, new RecipeType(new CustomItem(Material.LEAVES, config.getString("guide.recipe.berry"), 0)), true,
                new ItemStack[]{null, null, null, null, getItem(id + "_BUSH"), null, null, null, null})
                .register();
    }
}
