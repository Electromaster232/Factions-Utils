package me.djelectro.ifreveal

import io.github.toberocat.improvedfactions.user.factionUser
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import kotlin.math.ceil
import java.util.*

class RevealCommand(
    private val plugin: IFRevealPlugin,
    private val store: LeaveStore
) : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("ifreveal.reveal")) {
            msg(sender, plugin.config.getString("messages.no-permission")!!)
            return true
        }
        if (args.size != 1) {
            msg(sender, plugin.config.getString("messages.usage")!!)
            return true
        }

        val hoursRequired = plugin.config.getLong("hours-required", 48L)
        val requireOnline = plugin.config.getBoolean("require-online", true)
        val targetName = args[0]

        val onlineTarget: Player? = Bukkit.getPlayerExact(targetName)
        val targetUUID: UUID? = onlineTarget?.uniqueId ?: Bukkit.getOfflinePlayer(targetName)?.uniqueId

        if (targetUUID == null) {
            msg(sender, plugin.config.getString("messages.player-not-found")!!)
            return true
        }

        // Use ImprovedFactions API to check current faction status
        // uuid.factionUser() is provided by IF as a top-level extension
        val user = targetUUID.factionUser()
        val currentlyInFaction = user.isInFaction()

        if (currentlyInFaction) {
            msg(sender, plugin.config.getString("messages.not-eligible-currently-in-faction")!!)
            return true
        }

        val lastLeave = store.getLastLeave(targetUUID)
        if (lastLeave <= 0L) {
            // We’ve never seen them leave since this plugin has tracked events
            msg(sender, plugin.config.getString("messages.not-eligible-no-history")!!)
            return true
        }

        val now = System.currentTimeMillis()
        val elapsedMillis = now - lastLeave
        val requiredMillis = hoursRequired * 60L * 60L * 1000L

        if (elapsedMillis < requiredMillis) {
            val hoursElapsed = (elapsedMillis / 3600000.0)
            val remaining = ceil(hoursRequired - hoursElapsed).toLong().coerceAtLeast(1L)
            msg(
                sender,
                plugin.config.getString("messages.not-eligible-too-soon")!!
                    .replace("%hours%", String.format(Locale.US, "%.1f", hoursElapsed))
                    .replace("%remaining%", remaining.toString())
            )
            return true
        }

        // Past the grace window; only reveal exact XYZ if online (or config allows offline)
        if (requireOnline && (onlineTarget == null || !onlineTarget.isOnline)) {
            msg(sender, plugin.config.getString("messages.offline-not-allowed")!!)
            return true
        }

        val loc = onlineTarget?.location
        if (loc == null) {
            msg(sender, plugin.config.getString("messages.player-not-found")!!)
            return true
        }

        val out = plugin.config.getString("messages.reveal")!!
            .replace("%player%", onlineTarget.name)
            .replace("%world%", loc.world?.name ?: "unknown")
            .replace("%x%", String.format(Locale.US, "%.2f", loc.x))
            .replace("%y%", String.format(Locale.US, "%.2f", loc.y))
            .replace("%z%", String.format(Locale.US, "%.2f", loc.z))
            .replace("%yaw%", String.format(Locale.US, "%.1f", loc.yaw))
            .replace("%pitch%", String.format(Locale.US, "%.1f", loc.pitch))

        msg(sender, out)
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        if (args.size == 1) {
            val prefix = args[0].lowercase(Locale.getDefault())
            return Bukkit.getOnlinePlayers()
                .map { it.name }
                .filter { it.lowercase(Locale.getDefault()).startsWith(prefix) }
                .toMutableList()
        }
        return mutableListOf()
    }

    private fun msg(to: CommandSender, raw: String) {
        to.sendMessage(ChatColor.translateAlternateColorCodes('&', raw))
    }
}
