package io.github.qenas.homeSystem;

import io.github.qenas.homeSystem.manager.CooldownManager;
import io.github.qenas.homeSystem.manager.HomeManager;
import io.github.qenas.homeSystem.commands.Home;
import io.github.qenas.homeSystem.commands.SetHome;
import io.github.qenas.homeSystem.events.PlayerJoin;
import org.bukkit.plugin.java.JavaPlugin;

public final class HomeSystem extends JavaPlugin {

    @Override
    public void onEnable() {
        HomeManager homeManager = new HomeManager(this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(homeManager), this);
        getCommand("sethome").setExecutor(new SetHome(homeManager));
        getCommand("home").setExecutor(new Home(homeManager, new CooldownManager(this)));


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
