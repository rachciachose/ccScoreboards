// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccscoreboards.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerScoreEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private final Player namedPlayer;
    private int score;
    private String text;
    
    public PlayerScoreEvent(final Player player, final Player namedPlayer) {
        super(player);
        this.text = "";
        this.namedPlayer = namedPlayer;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerScoreEvent.handlers;
    }
    
    public HandlerList getHandlers() {
        return PlayerScoreEvent.handlers;
    }
    
    public Player getNamedPlayer() {
        return this.namedPlayer;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public void setScore(final int i) {
        this.score = i;
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(final String str) {
        this.text = str;
    }
    
    static {
        handlers = new HandlerList();
    }
}
