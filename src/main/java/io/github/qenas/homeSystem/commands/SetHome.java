package io.github.qenas.homeSystem.commands;

import io.github.qenas.homeSystem.manager.HomeManager;
import io.github.qenas.homeSystem.resourse.CoordinatesShower;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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

        World worldLocation = playerLocation.getWorld();

        if(worldLocation.getEnvironment().equals(World.Environment.NORMAL)) {
            homeManager.saveHome(player, playerLocation);

            player.sendMessage(ChatColor.GREEN + "» You have a home now, the coordinates are: ");
            CoordinatesShower.displayCoordinates(playerLocation, player);
        } else {
            player.sendMessage("§c» You can't use this command in " + worldLocation.getEnvironment().toString().toLowerCase());
        }



        return true;
    }
}
