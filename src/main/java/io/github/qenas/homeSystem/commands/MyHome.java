package io.github.qenas.homeSystem.commands;

import io.github.qenas.homeSystem.manager.HomeManager;
import io.github.qenas.homeSystem.resourse.CoordinatesShower;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MyHome implements CommandExecutor {

    private HomeManager homeManager;

    public MyHome (HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        Player player = (Player) commandSender;

        if(homeManager.hasHome(player)) {
            CoordinatesShower.showHome(homeManager.getHome(player), player);
        } else {
            player.sendMessage("» You do not have a home");
        }

        return true;
    }
}
