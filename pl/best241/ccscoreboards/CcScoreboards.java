// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccscoreboards;

import org.bukkit.event.Event;
import pl.best241.ccscoreboards.events.PlayerRefreshEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import pl.best241.ccscoreboards.listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class CcScoreboards extends JavaPlugin
{
    private static CcScoreboards plugin;
    
    public void onEnable() {
        CcScoreboards.plugin = this;
        PacketDataManager.loadProtocolManager();
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
    }
    
    public void onDisable() {
        PacketDataManager.unloadProtocolManager();
    }
    
    public static CcScoreboards getPlugin() {
        return CcScoreboards.plugin;
    }
    
    public static void refreshPlayer(final Player namedPlayer, final Player forWhom) {
        forWhom.hidePlayer(namedPlayer);
        Bukkit.getScheduler().runTaskLater((Plugin)getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                forWhom.showPlayer(namedPlayer);
                final PlayerRefreshEvent event = new PlayerRefreshEvent(forWhom, namedPlayer);
                Bukkit.getPluginManager().callEvent((Event)event);
            }
        }, 1L);
    }
    
    public static void refreshPlayer(final Player namedPlayer) {
        final Player[] onlinePlayers;
        final Player[] players = onlinePlayers = Bukkit.getOnlinePlayers();
        for (final Player player : onlinePlayers) {
            player.hidePlayer(namedPlayer);
        }
        Bukkit.getScheduler().runTaskLater((Plugin)getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                for (final Player player : players) {
                    player.showPlayer(namedPlayer);
                    final PlayerRefreshEvent event = new PlayerRefreshEvent(player, namedPlayer);
                    Bukkit.getPluginManager().callEvent((Event)event);
                }
            }
        }, 1L);
    }
    
    public static void refreshPlayersFor(final Player player) {
        final Player[] onlinePlayers;
        final Player[] players = onlinePlayers = Bukkit.getOnlinePlayers();
        for (final Player namedPlayer : onlinePlayers) {
            player.hidePlayer(namedPlayer);
        }
        Bukkit.getScheduler().runTaskLater((Plugin)getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                for (final Player namedPlayer : players) {
                    player.showPlayer(namedPlayer);
                    final PlayerRefreshEvent event = new PlayerRefreshEvent(player, namedPlayer);
                    Bukkit.getPluginManager().callEvent((Event)event);
                }
            }
        }, 1L);
    }
    
    public static void refreshPlayerScore(final Player player) {
    }
}
