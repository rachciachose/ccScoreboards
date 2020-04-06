// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccscoreboards;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.comphenix.protocol.events.PacketListener;
import org.bukkit.scoreboard.Score;
import org.bukkit.entity.Player;
import com.comphenix.protocol.events.PacketContainer;
import pl.best241.ccscoreboards.events.PlayerScoreEvent;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import pl.best241.ccscoreboards.events.PlayerPrefixSuffixEvent;
import com.comphenix.packetwrapper.WrapperPlayServerNamedEntitySpawn;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.ConnectionSide;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.plugin.Plugin;
import com.comphenix.protocol.ProtocolLibrary;
import java.util.ArrayList;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.ScoreboardManager;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.scoreboard.Scoreboard;
import java.util.HashMap;

public class PacketDataManager
{
    public static HashMap<Integer, Scoreboard> playerData;
    private static ProtocolManager protocolmanager;
    private static final ScoreboardManager manager;
    private static final Scoreboard board;
    private static final Objective objective;
    public static final HashMap<String, ArrayList<String>> teamsCreated;
    
    public static void loadProtocolManager() {
        PacketDataManager.protocolmanager = ProtocolLibrary.getProtocolManager();
        runListener();
    }
    
    public static void unloadProtocolManager() {
        PacketDataManager.protocolmanager.removePacketListeners((Plugin)CcScoreboards.getPlugin());
    }
    
    private static void runListener() {
        PacketDataManager.objective.setDisplayName("pkt");
        PacketDataManager.objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        PacketDataManager.protocolmanager.addPacketListener((PacketListener)new PacketAdapter(CcScoreboards.getPlugin(), ConnectionSide.SERVER_SIDE, ListenerPriority.NORMAL, new Integer[] { PacketType.Play.Server.NAMED_ENTITY_SPAWN.getLegacyId() }) {
            public void onPacketSending(final PacketEvent event) {
                if (event.getPacketType() != PacketType.Play.Server.NAMED_ENTITY_SPAWN) {
                    return;
                }
                final PacketContainer packetContainer = event.getPacket();
                final WrapperPlayServerNamedEntitySpawn packet = new WrapperPlayServerNamedEntitySpawn(packetContainer);
                final Player player = event.getPlayer();
                final int entityId = packet.getEntityID();
                final Player namedPlayer = getPlayerById(entityId);
                if (namedPlayer != null) {
                    final PlayerPrefixSuffixEvent teamEvent = new PlayerPrefixSuffixEvent(player, namedPlayer, namedPlayer.getName());
                    Bukkit.getPluginManager().callEvent((Event)teamEvent);
                    String prefix = teamEvent.getPrefix();
                    String suffix = teamEvent.getSuffix();
                    String tag = teamEvent.getTag();
                    if (prefix == null) {
                        prefix = "";
                    }
                    else if (prefix.length() > 16) {
                        prefix = prefix.substring(0, 16);
                    }
                    if (suffix == null) {
                        suffix = "";
                    }
                    else if (suffix.length() > 16) {
                        suffix = suffix.substring(0, 16);
                    }
                    if (tag == null) {
                        tag = namedPlayer.getName();
                    }
                    else if (tag.length() > 16) {
                        tag = tag.substring(0, 16);
                    }
                    packet.setPlayerName(tag);
                    byte friendlyFire = 0;
                    if (!teamEvent.canSee()) {
                        friendlyFire = 3;
                    }
                    PacketDataManager.sendTeamPacketUpdate(player, tag, tag, prefix, suffix, friendlyFire);
                    final PlayerScoreEvent scoreEvent = new PlayerScoreEvent(player, namedPlayer);
                    Bukkit.getPluginManager().callEvent((Event)scoreEvent);
                    final Score score = PacketDataManager.objective.getScore(tag);
                    score.setScore(scoreEvent.getScore());
                    namedPlayer.setScoreboard(PacketDataManager.board);
                    event.setPacket(packet.getHandle());
                }
            }
        });
        PacketDataManager.protocolmanager.addPacketListener((PacketListener)new PacketAdapter(CcScoreboards.getPlugin(), ConnectionSide.SERVER_SIDE, ListenerPriority.NORMAL, new Integer[] { PacketType.Play.Server.ENTITY_DESTROY.getLegacyId() }) {
            public void onPacketSending(final PacketEvent event) {
                final PacketContainer packet = event.getPacket().deepClone();
                final Player namedPlayer = getPlayerByArrayId((int[])packet.getIntegerArrays().read(0));
                final Player player = event.getPlayer();
                if (namedPlayer != null) {
                    PacketDataManager.sendTeamPacketRemove(player, namedPlayer.getName());
                }
            }
        });
    }
    
    private static Player getPlayerById(final int id) {
        for (final Player e : Bukkit.getOnlinePlayers()) {
            if (e.getEntityId() == id) {
                return e;
            }
        }
        return null;
    }
    
    private static Player getPlayerByArrayId(final int[] ids) {
        for (final int id : ids) {
            final Player player = getPlayerById(id);
            if (player != null) {
                return player;
            }
        }
        return null;
    }
    
    private static ProtocolManager getProtocolManager() {
        return PacketDataManager.protocolmanager;
    }
    
    public static boolean isTeamCreated(final String player, final String namedPlayer) {
        return PacketDataManager.teamsCreated.get(player).contains(namedPlayer);
    }
    
    private static void setTeamCreated(final String player, final String namedPlayer) {
        PacketDataManager.teamsCreated.get(player).add(namedPlayer);
    }
    
    private static void removeTeamCreated(final String player, final String namedPlayer) {
        PacketDataManager.teamsCreated.get(player).remove(namedPlayer);
    }
    
    public static void sendTeamPacketUpdate(final Player player, final String namedPlayer, final String displayName, final String prefix, final String suffix, final byte friendlyFire) {
        try {
            if (!isTeamCreated(player.getName(), namedPlayer)) {
                getProtocolManager().sendServerPacket(player, PacketManager.buildTeamPacketCreate(player.getName(), namedPlayer, displayName, prefix, suffix, (byte)0));
                setTeamCreated(player.getName(), namedPlayer);
            }
            else {
                getProtocolManager().sendServerPacket(player, PacketManager.buildTeamPacketUpdate(player.getName(), namedPlayer, displayName, prefix, suffix, (byte)0));
            }
        }
        catch (InvocationTargetException ex) {
            Logger.getLogger(PacketDataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void sendTeamPacketRemove(final Player player, final String namedPlayer) {
        try {
            if (isTeamCreated(player.getName(), namedPlayer)) {
                getProtocolManager().sendServerPacket(player, PacketManager.buildTeamPacketRemove(namedPlayer));
                removeTeamCreated(player.getName(), namedPlayer);
            }
        }
        catch (InvocationTargetException ex) {
            Logger.getLogger(PacketDataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void removeAllTeamPackets(final Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
    
    static {
        PacketDataManager.playerData = new HashMap<Integer, Scoreboard>();
        manager = Bukkit.getScoreboardManager();
        board = PacketDataManager.manager.getNewScoreboard();
        objective = PacketDataManager.board.registerNewObjective("lives", "dummy");
        teamsCreated = new HashMap<String, ArrayList<String>>();
    }
}
