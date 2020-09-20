package me.flash.flash.listeners

import me.flash.flash.Flash
import me.flash.flash.Flash.Companion.color
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Statistic
import org.bukkit.conversations.PlayerNamePrompt
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.*
import org.bukkit.event.server.ServerListPingEvent
import org.bukkit.plugin.java.JavaPlugin

class EventsListener : Listener {
    @EventHandler
    fun leave(event: PlayerQuitEvent) {
        event.player.world.players.forEach { players ->
            val name = event.player.name
            name.replace(
                    regex = Regex("Member"),
                    replacement = ""
            )
            players.sendMessage("&6[&3-&6] $name".color())
        }
        event.quitMessage = null // Take away the default (player) left the game
    }

    @EventHandler
    fun world(event: PlayerChangedWorldEvent) {
        val player = Bukkit.getPlayer(event.player.name)
        val name = player.name
        name.replace(
                regex = Regex("Member"),
                replacement = ""
        )
        event.from.players.forEach { players ->
            //player.sendMessage("test")
            player.sendMessage("&6[&3-&6] $name".color())
            if (players.hasPermission("Flash.fly")) {
                players.allowFlight = true
                players.isFlying = true
            }
        }
        event.player.world.players.forEach { player ->
            player.sendMessage("&6[&3+&6] ${event.player.name}".color())
        }
    }

    @EventHandler
    fun join(event: PlayerJoinEvent) {
        val playerer = Bukkit.getPlayer(event.player.name)
        playerer.teleport(Bukkit.getWorld("world").spawnLocation)
        event.player.world.players.forEach { players ->
            if (players.hasPermission("Flash.fly")) {
                players.allowFlight = true
                players.isFlying = true
                val player = Bukkit.getPlayer(event.player.name)
                val name = player.name
                name.replace(
                        regex = Regex("Member"),
                        replacement = ""
                )
            }
            event.joinMessage = null
        }
    }

}

@EventHandler
fun colors(event: AsyncPlayerChatEvent) {
    if (event.player.hasPermission("flash.colors")) {
        event.message = event.message.color()
    }
}

@EventHandler
fun motd(event: ServerListPingEvent) {
    //val motd = JavaPlugin.getPlugin(Flash::class.java).config.getStringList("motd")
    event.motd = "         \u00A76\u00A7lFlash's Server \u00A7c◀ 1.8 - 1.16 ▶\u00A7r\n                  \u00A7a\u00A7lKitPvP ◊ SkyBlock"
}

@EventHandler
fun onInventoryClick(event: InventoryClickEvent) {
    val player = event.whoClicked
    if (Flash.instance.config.getStringList("hub").contains(player.world.name)) {
        if (event.viewers.contains(player)) event.isCancelled = true
    }
}

@EventHandler
fun onPlayerDeath(event: PlayerDeathEvent) { // nope
    if (event.entity.killer !is Player) return
    event.entity.player.uniqueId.let { uuid ->
        Flash.sql.createStatement().execute("update data set deaths = deaths + 1 where uuid = '$uuid'")
        Flash.sql.commit()
    }
    event.entity.killer.uniqueId.let { uuid ->
        Flash.sql.createStatement().execute("update data set kills = kills + 1 where uuid = '$uuid'")
        Flash.sql.commit()
        // add the kill points
    }
}
// to get
//Flash.sql.createStatement().executeQuery("select kills from data where uuid = '${event.entity.killer.uniqueId}'")



