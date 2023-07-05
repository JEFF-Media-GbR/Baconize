package com.jeff_media.baconize;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class ConfigValues {

    private final boolean useDurability;
    private final boolean hurt;
    private final double hurtAmount;
    private final int cooldown;
    private final boolean turnToBaby;
    private final boolean hurtCanKill;

    public ConfigValues(FileConfiguration configuration) {
        this.useDurability = configuration.getBoolean("use-durability", true);
        this.hurt = configuration.getBoolean("hurt", true);
        this.hurtAmount = configuration.getDouble("hurt-amount", 0.0);
        this.cooldown = configuration.getInt("cooldown", 300);
        this.turnToBaby = configuration.getBoolean("turn-to-baby", true);
        this.hurtCanKill = configuration.getBoolean("hurt-can-kill", false);
    }

    public boolean shouldUseDurability() {
        return useDurability;
    }

    public boolean shouldHurt() {
        return hurt;
    }

    public double getHurtAmount() {
        return hurtAmount;
    }

    public int getCooldownTime() {
        return cooldown;
    }

    public boolean shouldTurnToBaby() {
        return turnToBaby;
    }

    public boolean getHurtCanKill() {
        return hurtCanKill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigValues that = (ConfigValues) o;
        return shouldUseDurability() == that.shouldUseDurability()
                && shouldHurt() == that.shouldHurt()
                && Double.compare(that.getHurtAmount(), getHurtAmount()) == 0
                && getCooldownTime() == that.getCooldownTime()
                && shouldTurnToBaby() == that.shouldTurnToBaby()
                && getHurtCanKill() == that.getHurtCanKill();
    }

    @Override
    public int hashCode() {
        return Objects.hash(shouldUseDurability(), shouldHurt(), getHurtAmount(), getCooldownTime(), shouldTurnToBaby(), getHurtCanKill());
    }

    @Override
    public String toString() {
        return "ConfigValues{" +
                "useDurability=" + useDurability +
                ", hurt=" + hurt +
                ", hurtAmount=" + hurtAmount +
                ", cooldown=" + cooldown +
                ", turnToBaby=" + turnToBaby +
                ", hurtCanKill=" + hurtCanKill +
                '}';
    }
}
