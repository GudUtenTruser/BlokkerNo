package me.tss1410.Blokker.Chat;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PlayerConnection;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatHandler {
	public Main pl;
	
	public ChatHandler(Main plugin){
		pl = plugin;
	}
	
	public PermissionGroup getGroupFromRank(Rank rank){
		switch(rank){
		case BRUKER:
			return PermissionsEx.getPermissionManager().getGroup("BRUKER");
		case SPONSOR:
			return PermissionsEx.getPermissionManager().getGroup("SPONSOR");
		case BYGGER:
			return PermissionsEx.getPermissionManager().getGroup("BYGGER");
		case HJELPER:
			return PermissionsEx.getPermissionManager().getGroup("HJELPER");
		case MOD:
			return PermissionsEx.getPermissionManager().getGroup("MOD");
		case ANSVARLIG:
			return PermissionsEx.getPermissionManager().getGroup("ANSVARLIG");
		case EIER:
			return PermissionsEx.getPermissionManager().getGroup("EIER");
		default:
			return PermissionsEx.getPermissionManager().getGroup("BRUKER");
		}
	}
	public String getPrefixFromRank(Rank rank){
		switch(rank){
		case BRUKER:
			return PermissionsEx.getPermissionManager().getGroup("BRUKER").getPrefix();
		case SPONSOR:
			return PermissionsEx.getPermissionManager().getGroup("SPONSOR").getPrefix();
		case BYGGER:
			return PermissionsEx.getPermissionManager().getGroup("BYGGER").getPrefix();
		case HJELPER:
			return PermissionsEx.getPermissionManager().getGroup("HJELPER").getPrefix();
		case MOD:
			return PermissionsEx.getPermissionManager().getGroup("MOD").getPrefix();
		case ANSVARLIG:
			return PermissionsEx.getPermissionManager().getGroup("ANSVARLIG").getPrefix();
		case EIER:
			return PermissionsEx.getPermissionManager().getGroup("EIER").getPrefix();
		default:
			return PermissionsEx.getPermissionManager().getGroup("BRUKER").getPrefix();
		}
	}
	


	public int getRankInt(Rank group){
		switch(group){
		case BRUKER:
			return 1;
		case SPONSOR:
			return 2;
		case BYGGER:
			return 3;
		case HJELPER:
			return 4;
		case MOD:
			return 5;
		case ANSVARLIG:
			return 6;
		case EIER:
			return 7;
		default:
			return 10;
		}
	}
	public Rank getRankFromInt(int i){
		switch(i){
		case 1:
			return Rank.BRUKER;
		case 2:
			return Rank.SPONSOR;
		case 3:
			return Rank.BYGGER;
		case 4:
			return Rank.HJELPER;
		case 5:
			return Rank.MOD;
		case 6:
			return Rank.ANSVARLIG;
		case 7:
			return Rank.EIER;
		default:
			return Rank.BRUKER;
		}
	}

	
	public void setPlayerPrefix(Player p){
		Spiller s = pl.spillere.get(p.getUniqueId());
		p.setDisplayName(getPrefixFromRank(s.getRank()) + " " + p.getName());
	}

	public void setRank(Player p, Rank rank){
		PermissionUser user = PermissionsEx.getUser(p);
		if(rank.equals(Rank.BRUKER)){
			user.removeGroup(pl.getPlayerHandler().getGroupFromPlayer(p));
			user.addGroup(getGroupFromRank(rank));
		}else if(rank.equals(Rank.SPONSOR)){
			user.removeGroup(pl.getPlayerHandler().getGroupFromPlayer(p));
			user.addGroup(getGroupFromRank(rank));
		}else if(rank.equals(Rank.BYGGER)){
			user.removeGroup(pl.getPlayerHandler().getGroupFromPlayer(p));
			user.addGroup(getGroupFromRank(rank));
		}else if(rank.equals(Rank.HJELPER)){
			user.removeGroup(pl.getPlayerHandler().getGroupFromPlayer(p));
			user.addGroup(getGroupFromRank(rank));
		}else if(rank.equals(Rank.MOD)){
			user.removeGroup(pl.getPlayerHandler().getGroupFromPlayer(p));
			user.addGroup(getGroupFromRank(rank));
		}else if(rank.equals(Rank.ANSVARLIG)){
			user.removeGroup(pl.getPlayerHandler().getGroupFromPlayer(p));
			user.addGroup(getGroupFromRank(rank));
		}else if(rank.equals(Rank.EIER)){
			user.removeGroup(pl.getPlayerHandler().getGroupFromPlayer(p));
			user.addGroup(getGroupFromRank(rank));
		}
	} 

	public void sendTitle1(final Player player, final Integer fadeIn, final Integer stay, final Integer fadeOut, String title, String subtitle) {
		final PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		final PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
		connection.sendPacket(packetPlayOutTimes);
		if (subtitle != null) {
			subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
			subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
			final IChatBaseComponent titleSub = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
			final PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, titleSub);
			connection.sendPacket(packetPlayOutSubTitle);
		}
		if (title != null) {
			title = title.replaceAll("%player%", player.getDisplayName());
			title = ChatColor.translateAlternateColorCodes('&', title);
			final IChatBaseComponent titleMain = ChatSerializer.a("{\"text\": \"" + title + "\"}");
			final PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleMain);
			connection.sendPacket(packetPlayOutTitle);
		}
	}

	public void sendTitle(final Player player, final Integer fadeIn, final Integer stay, final Integer fadeOut, final String title, final String subtitle) {
		sendTitle1(player, fadeIn, stay, fadeOut, title, subtitle);
	}
} 

