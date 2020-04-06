// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccscoreboards.events;

import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public class PlayerRefreshEvent extends PlayerEvent
{
    private final Player namedPlayer;
    private static final HandlerList handlers;
    
    public static HandlerList getHandlerList() {
        return PlayerRefreshEvent.handlers;
    }
    
    public PlayerRefreshEvent(final Player player, final Player namedPlayer) {
        super(player);
        this.namedPlayer = namedPlayer;
        this.player = player;
    }
    
    public HandlerList getHandlers() {
        return PlayerRefreshEvent.handlers;
    }
    
    public Player getNamedPlayer() {
        return this.namedPlayer;
    }
    
    static {
        handlers = new HandlerList();
    }
}
