package me.flash.flash.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class Enderchest : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = Bukkit.getPlayer(sender.name)
        if (player.hasPermission("flash.enderchest")) {
            player.openInventory(player.enderChest)
        }

    return true
    }
}

