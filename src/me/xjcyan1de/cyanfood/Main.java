package me.xjcyan1de.cyanfood;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public Server server;
    public FileConfiguration config;
    public String prefix;

    @Override
    public void onEnable() {
        server = getServer();
        try {
            config = getConfig();
            prefix = config.getString("prefix");
            saveDefaultConfig();
        } catch (Exception ex) {
            if (!(ex.getLocalizedMessage() == null))
                server.broadcastMessage("Ошибка запуска CyanFood " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void onDisable() {
        server.getScheduler().cancelTasks(this);
    }
}

