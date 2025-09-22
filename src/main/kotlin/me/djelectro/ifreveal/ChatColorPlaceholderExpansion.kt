package me.djelectro.ifreveal

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player

class ChatColorPlaceholderExpansion(    private val plugin: IFRevealPlugin,
                                        private val store: DataStore): PlaceholderExpansion() {
    override fun getIdentifier(): String {
        TODO("Not yet implemented")
    }

    override fun getAuthor(): String {
        TODO("Not yet implemented")
    }

    override fun getVersion(): String {
        TODO("Not yet implemented")
    }

    override fun onPlaceholderRequest(player: Player?, params: String): String? {
        return null;
    }
}