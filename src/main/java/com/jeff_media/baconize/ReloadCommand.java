package com.jeff_media.baconize;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final Baconize plugin;

    public ReloadCommand(Baconize plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.reload();
        sender.sendMessage(ChatColor.GREEN + "Baconize configuration reloaded.");
        return true;
    }
}
