package me.djelectro.ifreveal

import io.github.toberocat.improvedfactions.user.factionUser
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player

class ChatColorPlaceholderExpansion(    private val plugin: IFRevealPlugin,
                                        private val store: DataStore): PlaceholderExpansion() {
    override fun getIdentifier(): String {
        return "FactionChatColor";
    }

    override fun getAuthor(): String {
        return "DJElectro";
    }

    override fun getVersion(): String {
        return "1.0.0";
    }

    override fun onPlaceholderRequest(player: Player?, params: String): String {
        // Find the user's faction, if they have one
        val uuid = player!!.uniqueId;
        val factionPlayer = uuid.factionUser()
        if(!factionPlayer.isInFaction()){
            // If not in a faction, return white (default)
            return "&#FFFFFF";
        }

        val colorCode = plugin.store.getColor(factionPlayer.factionId)
        return "&#$colorCode"
    }
}