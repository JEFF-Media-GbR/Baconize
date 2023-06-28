package com.jeff_media.baconize;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Drop {

    private final Material defaultDrop;
    private final Material fireDrop;

    public Drop(Material defaultDrop, Material fireDrop) {
        this.defaultDrop = defaultDrop;
        this.fireDrop = fireDrop;
    }

    private Material getDrop0(boolean fire) {
        if(fire && fireDrop != null) {
            return fireDrop;
        } else {
            return defaultDrop;
        }
    }

    public ItemStack getDrop(boolean fire) {
        Material drop = getDrop0(fire);
        if(drop == null || drop == Material.AIR) {
            return null;
        } else {
            return new ItemStack(drop);
        }
    }

}
