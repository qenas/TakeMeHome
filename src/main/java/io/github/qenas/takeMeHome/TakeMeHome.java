package io.github.qenas.takeMeHome;

import io.github.qenas.takeMeHome.commands.MyHome;
import io.github.qenas.takeMeHome.manager.CooldownManager;
import io.github.qenas.takeMeHome.manager.HomeManager;
import io.github.qenas.takeMeHome.commands.Home;
import io.github.qenas.takeMeHome.commands.SetHome;
import io.github.qenas.takeMeHome.events.PlayerJoin;
import io.github.qenas.takeMeHome.manager.TeleportManager;
import io.github.qenas.takeMeHome.manager.ScoreboardManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TakeMeHome extends JavaPlugin {

    HomeManager homeManager;
    CooldownManager cooldownManager;
    TeleportManager teleportManager;
    ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        homeManager = new HomeManager(this);
        cooldownManager = new CooldownManager(this);
        scoreboardManager = new ScoreboardManager(this);
        teleportManager = new TeleportManager(this, cooldownManager, scoreboardManager);

        getServer().getPluginManager().registerEvents(new PlayerJoin(homeManager), this);
        getCommand("sethome").setExecutor(new SetHome(homeManager));
        getCommand("home").setExecutor(new Home(homeManager, teleportManager, this));
        getCommand("myhome").setExecutor(new MyHome(homeManager));


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
