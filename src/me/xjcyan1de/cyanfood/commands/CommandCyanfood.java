package me.xjcyan1de.cyanfood.commands;

import me.xjcyan1de.cyanfood.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class CommandCyanfood extends Command {
    private final Main main;

    public CommandCyanfood(Main main) {
        super
                (
                        "cyanfood",
                        "Главная команда CyanFood",
                        "/cyanfood",
                        new ArrayList<>()
                );
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {

        return true;
    }
}
