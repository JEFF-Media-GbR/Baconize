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
        entityCooldown = new Cooldown(TimeUnit.SECONDS);
        dropManager = new DropManager(this, Objects.requireNonNull(getConfig().getConfigurationSection("drops"), "Missing drops section in config."));
    }

    public DropManager getDropManager() {
        return dropManager;
    }

    public Cooldown getEntityCooldownManager() {
        return entityCooldown;
    }

    /*
    Configuration
     */
    public boolean shouldUseDurability() {
        return getConfig().getBoolean("use-durability", true);
    }

    public boolean shouldHurt() {
        return getConfig().getBoolean("hurt", true);
    }

    public double getHurtAmount() {
        return getConfig().getDouble("hurt-amount", 0.0);
    }

    public int getCooldownTime() {
        return getConfig().getInt("cooldown", 300);
    }

    public boolean shouldTurnToBaby() {
        return getConfig().getBoolean("turn-to-baby", true);
    }

    public boolean getHurtCanKill() {
        return getConfig().getBoolean("hurt-can-kill", false);
    }
}
