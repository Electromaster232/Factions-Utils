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

class FactionChatColor ( private val plugin: IFRevealPlugin, private val store: DataStore) : CommandExecutor {
    override fun onCommand(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>?
    ): Boolean {


        // Try to find the Sender's faction
        val factionUser  = Bukkit.getPlayer(p0.name)!!.uniqueId.factionUser()

        if(!factionUser.isInFaction()) {
            p0.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "You are not in a Faction.")
            return false
        }

        // Determine if they are admin of the faction
        if(!factionUser.hasPermission("chat.color")){
            p0.sendMessage(ChatColor.RED.toString() + "You don't have permission to use this command!")
            return false
        }

        val color = p3?.get(0)
        if(color == null) {
            p0.sendMessage(ChatColor.RED.toString() + "You did not specify a color!");
            return false
        }

        if(color.startsWith("#")) {
            color.trimStart('#')
        }

        store.setColor(factionUser.factionId, color)
        p0.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Faction chat color successfully set!")
        return true

    }
}