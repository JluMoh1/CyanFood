package me.xjcyan1de.cyanfood;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import static me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem.getItem;

public class Main extends JavaPlugin {
    public Server server;
    public FileConfiguration config;
    public String prefix;
    public Category category_plants, category_drinks, category_food;

    public ItemStack getSkull(MaterialData material, String texture) {
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

    @Override
    public void onEnable() {
        server = getServer();
        try {
            config = getConfig();
            prefix = config.getString("prefix");
            saveDefaultConfig();
            category_plants = new Category(new CustomItem(getSkull(Material.NETHER_STALK, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTVhNWM0YTBhMTZkYWJjOWIxZWM3MmZjODNlMjNhYzE1ZDAxOTdkZTYxYjEzOGJhYmNhN2M4YTI5YzgyMCJ9fX0="), "§7Растения и фрукты", "", "§a> Кликни, чтобы открыть"));
            registerItems();
        } catch (Exception ex) {
            if (!(ex.getLocalizedMessage() == null))
                server.broadcastMessage("Ошибка запуска CyanFood " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void onDisable() {
        server.getScheduler().cancelTasks(this);
    }

    public void registerItems() {
        registerBerry("GRAPE", PlantType.BUSH, new PlantData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmVlOTc2NDliZDk5OTk1NTQxM2ZjYmYwYjI2OWM5MWJlNDM0MmIxMGQwNzU1YmFkN2ExN2U5NWZjZWZkYWIwIn19fQ=="), "&cВиноград", "&cВиноградный куст");
    }

    public void registerBerry(String id, PlantType type, PlantData data, String berryname, String bushname) {
        Berry berry = new Berry(id, type, data);

        new SlimefunItem(category_plants, new CustomItem(Material.SAPLING, bushname, 0), id + "_BUSH", new RecipeType(new CustomItem(Material.LONG_GRASS, "&7Выпадение с травы", 1)),
                new ItemStack[]{null, null, null, null, new CustomItem(Material.LONG_GRASS, 1), null, null, null, null})
                .register();

        new Plant(category_plants, new CustomItem(getSkull(Material.NETHER_STALK, data.getTexture()), berryname), id, new RecipeType(new CustomItem(Material.LEAVES, "&7Добывается с определённого куста", 0)), true,
                new ItemStack[]{null, null, null, null, getItem(id + "_BUSH"), null, null, null, null})
                .register();
    }
}

