// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccscoreboards.data;

import java.util.ArrayList;

public class PlayerData
{
    private final String player;
    private final ArrayList<String> playersInRange;
    
    public PlayerData(final String player, final ArrayList<String> playersInRange) {
        this.player = player;
        this.playersInRange = playersInRange;
    }
    
    public String getPlayer() {
        return this.player;
    }
    
    public boolean isPlayerInRange(final String namedPlayer) {
        return this.playersInRange.contains(namedPlayer);
    }
    
    public void addPlayerInRange(final String namedPlayer) {
        this.playersInRange.add(namedPlayer);
    }
    
    public void removePlayerInRange(final String namedPlayer) {
        this.playersInRange.remove(namedPlayer);
    }
}
