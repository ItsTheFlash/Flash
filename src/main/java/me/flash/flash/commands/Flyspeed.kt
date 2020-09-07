package me.flash.flash.commands


import me.flash.flash.Flash
import me.flash.flash.Flash.Companion.colour
import me.flash.flash.Flash.Companion.error
import me.flash.flash.Flash.Companion.noPermission
import me.flash.flash.Flash.Companion.notPlayer
import me.flash.flash.Flash.Companion.prefix
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Flyspeed : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) sender.sendMessage(notPlayer).run { return true }
        if (!sender.hasPermission("flash.flyspeed")) sender.sendMessage(noPermission).run { return true }
        val speed = args.firstOrNull()?.toIntOrNull() ?: sender.sendMessage("&cUsage: /flyspeed <speed>".prefix()).run { return true }
        if (!IntRange(1, 10).contains(speed)) sender.sendMessage("Please specify a whole number 1 and 10.".error()).run { return true }
        sender.flySpeed = speed.toFloat()/10
        sender.sendMessage("Set flight speed for &l${sender.name} &6to $speed".prefix())
        Flash.staffMessage(sender, "Set their flight speed to $speed".colour())
        return true
    }
}