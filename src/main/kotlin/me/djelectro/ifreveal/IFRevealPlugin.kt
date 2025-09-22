package me.djelectro.ifreveal
import org.bukkit.plugin.java.JavaPlugin

class IFRevealPlugin : JavaPlugin() {
    lateinit var store: LeaveStore
        private set

    override fun onEnable() {
        saveDefaultConfig()
        store = LeaveStore(this)
        // Register IF events (join/leave)
        server.pluginManager.registerEvents(IFEvents(this, store), this)
        // Register command
        val cmd = getCommand("revealplayer")
        val executor = RevealCommand(this, store)
        cmd?.setExecutor(executor)
        cmd?.tabCompleter = executor

        logger.info("IF-Reveal enabled.")
    }

    override fun onDisable() {
        store.save()
        logger.info("IF-Reveal disabled.")
    }
}
