package me.flash.flash.commands

import me.flash.flash.FlashUtil
import me.flash.flash.FlashUtil.Companion.notPlayer
import me.flash.flash.FlashUtil.Companion.prefix
import me.flash.flash.commands.api.FlashCommand
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Feed : FlashCommand("feed") {

    init {
        usage = "[player]"
        description = "Feed a player"
    }

    override fun run() {
        if (args.isEmpty()) {
            checkPlayer()
            checkPerm("flash.feed")
            getPlayer().foodLevel = Int.MAX_VALUE
            msg("You have been fed".prefix())
            FlashUtil.staffMessage(sender, "Fed themself.")
            return
        }
        checkPerm("flash.feed.others")
        val player = getTarget(0)
        if (player == sender) {
            player.foodLevel = Int.MAX_VALUE
            msg("You have been fed".prefix())
            return
        }
        player.foodLevel = Int.MAX_VALUE
        msg(player, "You were fed by ${nice()}${sender.name}".prefix())
        msg("You have fed ${nice()}${player.name}".prefix())
        FlashUtil.staffMessage(sender, "fed ${player.name}")
    }
}