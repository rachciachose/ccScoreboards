// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccscoreboards.events;

import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public class PlayerPrefixSuffixEvent extends PlayerEvent
{
    private String tag;
    private String prefix;
    private String suffix;
    private final Player namedPlayer;
    private boolean canSee;
    private static final HandlerList handlers;
    
    public static HandlerList getHandlerList() {
        return PlayerPrefixSuffixEvent.handlers;
    }
    
    public PlayerPrefixSuffixEvent(final Player player, final Player namedPlayer, final String tag) {
        super(player);
        this.namedPlayer = namedPlayer;
        this.tag = tag;
        this.prefix = "";
        this.suffix = "";
        this.canSee = true;
    }
    
    public void setTag(final String playerTag) {
        this.tag = playerTag;
    }
    
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
    
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }
    
    public Player getNamedPlayer() {
        return this.namedPlayer;
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public String getSuffix() {
        return this.suffix;
    }
    
    public boolean canSee() {
        return this.canSee;
    }
    
    public void setCanSee(final boolean value) {
        this.canSee = value;
    }
    
    public HandlerList getHandlers() {
        return PlayerPrefixSuffixEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
