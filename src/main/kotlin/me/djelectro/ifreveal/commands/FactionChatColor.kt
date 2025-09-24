package me.djelectro.ifreveal.commands

import io.github.toberocat.improvedfactions.user.factionUser
import io.papermc.paper.command.brigadier.argument.ArgumentTypes.player
import me.djelectro.ifreveal.DataStore
import me.djelectro.ifreveal.IFRevealPlugin
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.UUID

class FactionChatColor ( private val plugin: IFRevealPlugin, private val store: DataStore) : CommandExecutor {
    override fun onCommand(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>?
    ): Boolean {

        if (p0 !is Player) {
            p0.sendMessage("Players only.")
            return true
        }

        if(!p0.hasPermission("chat.changecolor")){
            p0.sendMessage("You do not have permission to change the color.")
            return true
        }

        // Try to find the Sender's faction
        val factionUser = p0.factionUser()


        if(!factionUser.isInFaction()) {
            p0.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "You are not in a Faction.")
            return true
        }
        var factionId = factionUser.factionId

        val color = p3?.get(0)
        if(color == null) {
            p0.sendMessage(ChatColor.RED.toString() + "You did not specify a color!");
            return true
        }

        if(color.startsWith("#")) {
            color.trimStart('#')
        }

        store.setColor(factionId, color)
        p0.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Faction chat color successfully set!")
        return true

    }
}