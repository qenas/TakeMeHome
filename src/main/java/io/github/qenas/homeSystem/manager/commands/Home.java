package io.github.qenas.homeSystem.manager.commands;

import io.github.qenas.homeSystem.manager.HomeManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Home implements CommandExecutor {

    private HomeManager homeManager;

    public Home (HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(!(commandSender instanceof Player)) {
            return true;
        }

        Player player = (Player) commandSender;

        if(homeManager.playerHasHome(player)) {
            Location playerHome = homeManager.getHome(player);
            player.teleport(playerHome);
            player.sendMessage(ChatColor.GREEN + "» You have been teleported to your house.");
        } else {
            player.sendMessage("» You do not have a home");
        }


        return true;
    }
}
