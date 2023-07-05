package com.jeff_media.baconize;

import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.data.Cooldown;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class Baconize extends JavaPlugin implements Listener {

    private DropManager dropManager;
    private Cooldown entityCooldown;
    public ConfigValues configValues;

    {
        JeffLib.init(this);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reload();
        getServer().getPluginManager().registerEvents(new ShearListener(this), this);
        Objects.requireNonNull(getCommand("baconize"), "Stop messing with plugin.yml!").setExecutor(new ReloadCommand(this));
    }

    public void reload() {
        reloadConfig();
        initConfig();
        entityCooldown = new Cooldown(TimeUnit.SECONDS);
        dropManager = new DropManager(this, Objects.requireNonNull(getConfig().getConfigurationSection("drops"), "Missing drops section in config."));
    }

    private void initConfig() {
        configValues = new ConfigValues(getConfig());
    }

    public DropManager getDropManager() {
        return dropManager;
    }

    public Cooldown getEntityCooldownManager() {
        return entityCooldown;
    }

    public ConfigValues getConfigValues() {
        return configValues;
    }
}
