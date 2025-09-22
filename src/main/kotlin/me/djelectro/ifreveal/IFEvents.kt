package me.djelectro.ifreveal

import io.github.toberocat.improvedfactions.api.events.FactionJoinEvent
import io.github.toberocat.improvedfactions.api.events.FactionLeaveEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * Listens to ImprovedFactions events and records the last time a player left a faction.
 * These events exist in the IF API. (Join: FactionJoinEvent, Leave: FactionLeaveEvent)
 */
class IFEvents(
    private val plugin: IFRevealPlugin,
    private val store: DataStore
) : Listener {

    @EventHandler
    fun onJoin(event: FactionJoinEvent) {
        plugin.logger.fine("${event.user.uniqueId} joined faction ${event.faction.name}")
    }

    @EventHandler
    fun onLeave(event: FactionLeaveEvent) {
        store.setLastLeave(event.user.uniqueId, System.currentTimeMillis(), event.faction.name)
        plugin.logger.fine("${event.user.uniqueId} left faction ${event.faction.name}")
    }
}
