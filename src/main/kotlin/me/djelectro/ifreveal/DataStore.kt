package me.djelectro.ifreveal

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.UUID

class DataStore(private val plugin: IFRevealPlugin) {
    private val file = File(plugin.dataFolder, "leaves.yml")
    private val config = YamlConfiguration()

    init {
        if (!plugin.dataFolder.exists()) plugin.dataFolder.mkdirs()
        if (!file.exists()) file.createNewFile()
        config.load(file)
    }

    fun getLastLeave(uuid: UUID): Long {
        return config.getLong("players.$uuid.lastLeave", 0L)
    }

    fun getLastFaction(uuid: UUID): String? {
        return config.getString("players.$uuid.lastFaction")
    }

    fun setLastLeave(uuid: UUID, millis: Long, factionName: String?) {
        config.set("players.$uuid.lastLeave", millis)
        if (factionName != null) {
            config.set("players.$uuid.lastFaction", factionName)
        }
        save() // persist eagerly to survive crashes
    }

    fun save() {
        config.save(file)
    }
}
