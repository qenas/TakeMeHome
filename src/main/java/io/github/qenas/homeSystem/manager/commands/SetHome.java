package io.github.qenas.homeSystem.manager.commands;

import io.github.qenas.homeSystem.manager.HomeManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetHome implements CommandExecutor {
    private HomeManager homeManager;

    public SetHome (HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(!(commandSender instanceof Player)){
            return true;
        }

        Player player = (Player) commandSender;

        Location playerLocation = player.getLocation();

        homeManager.saveHome(player, playerLocation);

        player.sendMessage(ChatColor.GREEN + "» You have a home now, the coordinates are: ");
        player.sendMessage(ChatColor.GOLD + "X: " + ChatColor.WHITE + playerLocation.getX());
        player.sendMessage(ChatColor.GOLD + "Y: " + ChatColor.WHITE + playerLocation.getY());
        player.sendMessage(ChatColor.GOLD + "Z: " + ChatColor.WHITE + playerLocation.getZ());

        return true;
    }
}
