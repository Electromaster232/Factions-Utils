package me.djelectro.ifreveal
import me.djelectro.ifreveal.commands.FactionChatColor
import me.djelectro.ifreveal.commands.RevealCommand
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class IFRevealPlugin : JavaPlugin() {
    lateinit var store: DataStore
        private set

    override fun onEnable() {
        saveDefaultConfig()
        store = DataStore(this)
        // Register IF events (join/leave)
        server.pluginManager.registerEvents(IFEvents(this, store), this)
        // Register command
        val cmd = getCommand("revealplayer")
        val executor = RevealCommand(this, store)
        cmd?.setExecutor(executor)
        cmd?.tabCompleter = executor

        val cmd2 = getCommand("chatcolor")
        val executor2 = FactionChatColor(this, store)
        cmd2?.setExecutor(executor2)

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) { //
            ChatColorPlaceholderExpansion(this, store).register()
        }
        logger.info("IF-Utils enabled.")
    }

    override fun onDisable() {
        store.save()
        logger.info("IF-Utils disabled.")
    }
}
