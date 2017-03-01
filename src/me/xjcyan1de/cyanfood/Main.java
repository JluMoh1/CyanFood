package me.xjcyan1de.cyanfood;

import me.mrCookieSlime.Slimefun.Objects.Category;
import org.bukkit.Server;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {
    public Server server;
    public FileConfiguration config;
    public String prefix;
    public Category category_plants;
    public List<ItemStack> saplings;
    public List<Berry> berries;
    public ItemRegistry itemRegistry;
    public PlantListener plantListener;
    public FoodListener foodListener;
    public BlockFace[] bf = {BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST};

    @Override
    public void onEnable() {
        server = getServer();
        try {
            config = getConfig();
            prefix = config.getString("prefix");
            saveDefaultConfig();
            saplings = new ArrayList<ItemStack>();
            berries = new ArrayList<Berry>();
            itemRegistry = new ItemRegistry(this);
            plantListener = new PlantListener(this);
            foodListener = new FoodListener(this);
        } catch (Exception ex) {
            server.broadcastMessage("Ошибка запуска CyanFood " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void onDisable() {
        server.getScheduler().cancelTasks(this);
    }
}

