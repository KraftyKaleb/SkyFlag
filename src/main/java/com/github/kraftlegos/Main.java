package com.github.kraftlegos;

import com.github.kraftlegos.commands.ForceStart;
import com.github.kraftlegos.commands.Join;
import com.github.kraftlegos.listeners.*;
import com.github.kraftlegos.managers.GameManager;
import com.github.kraftlegos.object.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public final class Main extends JavaPlugin {

    private static Main instance;
    private Set<Game> games = new HashSet<>();
    private int gamesList = 0;

    public static Main getInstance() {
        return instance;
    }

    public static Main get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        //getConfig().options().copyDefaults(true);
        //getConfig().options().copyHeader(true);
        //saveDefaultConfig();
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new onQuit(), this);
        pm.registerEvents(new OnJoin(), this);
        pm.registerEvents(new OnPlayerDamage(this), this);
        pm.registerEvents(new OnFoodChange(), this);
        pm.registerEvents(new OnDeath(), this);
        pm.registerEvents(new OnInteractEvent(this), this);
        pm.registerEvents(new OnHelmetChange(), this);

        getCommand("join").setExecutor(new Join());
        getCommand("forcestart").setExecutor(new ForceStart());
        getLogger();

        Game game = new Game("one");
        GameManager.addGame(game);
        this.registerGame(game);
        game.setState(Game.GameState.LOBBY);
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getScoreboardManager().getMainScoreboard().getObjective("line").unregister();

        instance = null;

    }

    public boolean registerGame(Game game) {
        if (games.size() == gamesList && gamesList != 1) {
            return false;
        }
        games.add(game);

        return true;
    }


}
