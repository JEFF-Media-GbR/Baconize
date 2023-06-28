package com.jeff_media.baconize;

import com.jeff_media.jefflib.EnumUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DropManager {

    private final Map<EntityType, Drop> drops = new HashMap<>();

    public DropManager(Plugin plugin, ConfigurationSection dropSection) {
        for(String key : dropSection.getKeys(false)) {
            EntityType type = EnumUtils.getIfPresent(EntityType.class, key).orElse(null);
            if(type == null) {
                plugin.getLogger().warning("Invalid EntityType: " + key);
                continue;
            }
            String defaultDropName = null;
            String fireDropName = null;
            if(dropSection.isString(key)) {
                defaultDropName = dropSection.getString(key);
            } else if(dropSection.isConfigurationSection(key)) {
                ConfigurationSection innerDropSection = Objects.requireNonNull(dropSection.getConfigurationSection(key));
                if(innerDropSection.isString("default")) {
                    defaultDropName = innerDropSection.getString("default");
                }
                if(innerDropSection.isString("fire")) {
                    fireDropName = innerDropSection.getString("fire");
                }
            } else {
                plugin.getLogger().warning("Invalid drop configuration for EntityType " + key + ".");
                continue;
            }
            Material defaultDrop = null;
            if(defaultDropName != null) {
                defaultDrop = EnumUtils.getIfPresent(Material.class, defaultDropName).orElse(null);
                if(defaultDrop == null || !defaultDrop.isItem()) {
                    plugin.getLogger().warning("Invalid item: " + defaultDropName);
                    //continue;
                }
            }

            Material fireDrop = null;
            if(fireDropName != null) {
                fireDrop = EnumUtils.getIfPresent(Material.class, fireDropName).orElse(null);
                if(fireDrop == null || !fireDrop.isItem()) {
                    plugin.getLogger().warning("Invalid item: " + defaultDropName);
                    //continue;
                }
            }

            drops.put(type, new Drop(defaultDrop, fireDrop));
        }
    }

    public boolean isEnabled(EntityType type) {
        return drops.containsKey(type);
    }

    public ItemStack getDrop(EntityType type, boolean isOnFire) {
        return drops.get(type).getDrop(isOnFire);
    }
}
