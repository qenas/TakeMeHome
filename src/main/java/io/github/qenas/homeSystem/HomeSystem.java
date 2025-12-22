package io.github.qenas.homeSystem;

import io.github.qenas.homeSystem.manager.CooldownManager;
import io.github.qenas.homeSystem.manager.HomeManager;
import io.github.qenas.homeSystem.commands.Home;
import io.github.qenas.homeSystem.commands.SetHome;
import io.github.qenas.homeSystem.events.PlayerJoin;
import io.github.qenas.homeSystem.manager.TeleportManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class HomeSystem extends JavaPlugin {

    HomeManager homeManager = new HomeManager(this);
    CooldownManager cooldownManager = new CooldownManager(this);
    TeleportManager teleportManager = new TeleportManager(this, cooldownManager);

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new PlayerJoin(homeManager), this);
        getCommand("sethome").setExecutor(new SetHome(homeManager));
        getCommand("home").setExecutor(new Home(homeManager, teleportManager, this));


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
