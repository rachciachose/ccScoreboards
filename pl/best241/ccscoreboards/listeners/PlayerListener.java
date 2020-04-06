// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccscoreboards.listeners;

import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import pl.best241.ccscoreboards.PacketDataManager;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    public static void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        PacketDataManager.removeAllTeamPackets(player);
        PacketDataManager.teamsCreated.put(player.getName(), new ArrayList<String>());
    }
    
    @EventHandler
    public static void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        PacketDataManager.removeAllTeamPackets(player);
    }
    
    @EventHandler
    public static void onKick(final PlayerKickEvent event) {
        final Player player = event.getPlayer();
        PacketDataManager.removeAllTeamPackets(player);
    }
}
