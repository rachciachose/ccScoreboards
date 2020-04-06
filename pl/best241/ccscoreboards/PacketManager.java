// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccscoreboards;

import com.comphenix.packetwrapper.WrapperPlayServerScoreboardTeam;
import com.comphenix.protocol.events.PacketContainer;

public class PacketManager
{
    public static PacketContainer buildTeamPacketCreate(final String player, final String namedPlayer, final String displayName, final String prefix, final String suffix, final Byte friendlyFire) {
        final WrapperPlayServerScoreboardTeam teamPacket = new WrapperPlayServerScoreboardTeam();
        teamPacket.setPacketMode((byte)0);
        teamPacket.setTeamName(namedPlayer);
        teamPacket.setTeamDisplayName(displayName);
        teamPacket.setTeamPrefix(prefix);
        teamPacket.setTeamSuffix(suffix);
        teamPacket.getPlayers().add(player);
        teamPacket.getPlayers().add(namedPlayer);
        teamPacket.setFriendlyFire(friendlyFire);
        return teamPacket.getHandle();
    }
    
    public static PacketContainer buildTeamPacketRemove(final String namedPlayer) {
        final WrapperPlayServerScoreboardTeam teamPacket = new WrapperPlayServerScoreboardTeam();
        teamPacket.setPacketMode((byte)1);
        teamPacket.setTeamName(namedPlayer);
        return teamPacket.getHandle();
    }
    
    public static PacketContainer buildTeamPacketUpdate(final String player, final String namedPlayer, final String displayName, final String prefix, final String suffix, final Byte friendlyFire) {
        final WrapperPlayServerScoreboardTeam teamPacket = new WrapperPlayServerScoreboardTeam();
        teamPacket.setPacketMode((byte)2);
        teamPacket.setTeamName(namedPlayer);
        teamPacket.setTeamDisplayName(displayName);
        teamPacket.setTeamPrefix(prefix);
        teamPacket.setTeamSuffix(suffix);
        teamPacket.getPlayers().add(player);
        teamPacket.getPlayers().add(namedPlayer);
        teamPacket.setFriendlyFire(friendlyFire);
        return teamPacket.getHandle();
    }
}
