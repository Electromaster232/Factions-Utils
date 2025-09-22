package me.djelectro.ifreveal.commands

import me.djelectro.ifreveal.DataStore
import me.djelectro.ifreveal.IFRevealPlugin
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
        TODO("Not yet implemented")
    }
}