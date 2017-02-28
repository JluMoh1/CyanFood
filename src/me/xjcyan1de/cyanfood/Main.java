package me.xjcyan1de.cyanfood;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import me.mrCookieSlime.Slimefun.Objects.Category;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public Server server;
    public FileConfiguration config;
    public String prefix;
    public Category category_plants, category_drinks, category_food;

    @Override
    public void onEnable() {
        server = getServer();
        try {
            config = getConfig();
            prefix = config.getString("prefix");
            saveDefaultConfig();
            category_plants = new Category(new CustomItem(getSkull(Material.NETHER_STALK, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTVhNWM0YTBhMTZkYWJjOWIxZWM3MmZjODNlMjNhYzE1ZDAxOTdkZTYxYjEzOGJhYmNhN2M4YTI5YzgyMCJ9fX0="), "§7Растения и фрукты", "", "§a> Кликни, чтобы открыть"));

        } catch (Exception ex) {
            if (!(ex.getLocalizedMessage() == null))
                server.broadcastMessage("Ошибка запуска CyanFood " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void onDisable() {
        server.getScheduler().cancelTasks(this);
    }

    private ItemStack getSkull(Material material, String texture) {
        return getSkull(new MaterialData(material), texture);
    }

    public static ItemStack getSkull(MaterialData material, String texture) {
        try {
            if (texture.equals("NO_SKULL_SPECIFIED")) return material.toItemStack(1);
            return CustomSkull.getItem(texture);
        } catch (Exception ex) {
            ex.printStackTrace();
            return material.toItemStack(1);
        }
    }
}

