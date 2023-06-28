package com.jeff_media.baconize;

import com.jeff_media.jefflib.ItemStackUtils;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ShearListener implements Listener {

    private final Baconize plugin;

    public ShearListener(Baconize plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onShearPig(PlayerInteractEntityEvent event) {

        Player player = event.getPlayer();

        if(!(event.getRightClicked() instanceof LivingEntity)) {
            return;
        }

        LivingEntity entity = (LivingEntity) event.getRightClicked();

        ItemStack item = player.getInventory().getItem(event.getHand());
        if(item.getType() != Material.SHEARS) {
            return;
        }

        DropManager dropManager = plugin.getDropManager();
        if(!dropManager.isEnabled(entity.getType())) {
            return;
        }

        boolean checkForCooldown = true;

        if(entity instanceof Ageable) {
            Ageable ageable = (Ageable) entity;

            if(!ageable.isAdult()) {
                return;
            }

            if(plugin.shouldTurnToBaby()) {
                ageable.setBaby();
                checkForCooldown = false;
            }
        }

        if(checkForCooldown) {
            if(plugin.getEntityCooldownManager().hasCooldown(entity)) {
                return;
            } else {
                plugin.getEntityCooldownManager().setCooldown(entity, plugin.getCooldownTime(), TimeUnit.SECONDS);
            }
        }

        ItemMeta meta = Objects.requireNonNull(item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(Material.SHEARS));
        boolean isOnFire = entity.getFireTicks() > 0 || meta.getEnchantLevel(Enchantment.FIRE_ASPECT) > 0 || meta.getEnchantLevel(Enchantment.ARROW_FIRE) > 0;

        World world = entity.getWorld();

        world.playSound(entity.getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);

        if(plugin.shouldUseDurability() && player.getGameMode() != GameMode.CREATIVE) {
            ItemStackUtils.damageItem(1, item, player);
        }

        if(plugin.shouldHurt()) {
            double damageAmount = plugin.getHurtAmount();
            double health = entity.getHealth();

            if(health > damageAmount) {
                entity.damage(damageAmount, player);
            } else {
                if(plugin.getHurtCanKill()) {
                    entity.damage(damageAmount, player);
                    return; // Don't drop additional drops
                } else {
                    if(health >= damageAmount + 1) {
                        entity.damage(health - 1, player);
                    }else {
                        entity.damage(0, player);
                    }
                }
            }
        }

        ItemStack drop = dropManager.getDrop(entity.getType(), isOnFire);
        world.dropItemNaturally(entity.getLocation(), drop);


    }
}
