package me.xjcyan1de.cyanfood;

import org.bukkit.event.Listener;

public class PlantListener implements Listener {
    private final Main main;

    public PlantListener(Main main) {
        this.main = main;
        main.server.getPluginManager().registerEvents(this, main);
    }


}
