package me.flash.flash.commands

import me.flash.flash.Flash
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Vanish : CommandExecutor {
    companion object {
        var vanishedPlayers = emptyList<Player>().toMutableList()
    }
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Flash.notPlayer)
        } else {
            if (!sender.hasPermission("k.vanish")) {
                sender.sendMessage(Flash.noPermission)
                return true
            }
            if (args.isEmpty()) {
                if (vanishedPlayers.contains(sender)) {
                    Bukkit.getOnlinePlayers().forEach { player ->
                        player.showPlayer(sender)
                    }
                } else {
                    Bukkit.getOnlinePlayers().forEach { player->
                        if (!player.hasPermission("k.vanish.see")) {
                            player.hidePlayer(sender)
                        }
                    }
                }

            }

        }
        return true
    }
}