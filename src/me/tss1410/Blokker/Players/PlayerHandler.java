package me.tss1410.Blokker.Players;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Chat.Rank;

public class PlayerHandler {
	
	public Main pl;
	
	public PlayerHandler(Main plugin){
		pl=plugin;
	}
	
	public String getNameFromUUID(UUID uuid){
		Spiller s = pl.spillere.get(uuid);
			return s.getName();
	}
	
	public String getNameFromId(int id){
		for(Spiller s : pl.spillere.values()){
			if(s.getId() == id){
				return s.getName();
			}
		}
		return null;
	}
	
	public UUID getUUIDFromId(int id){
		for(Spiller s : pl.spillere.values()){
			if(s.getId() == id){
				return s.getUUID();
			}
		}
		return null;
	}
	
	public UUID getUUIDFromName(String name){
		for(Spiller s : pl.spillere.values()){
			if(s.getName().equalsIgnoreCase(name)){
				return s.getUUID();
			}
		}
		return null;
	}
	
	public int getIdFromName(String name){
		for(Spiller s : pl.spillere.values()){
			if(s.getName().equalsIgnoreCase(name)){
				return s.getId();
			}
		}
		return -3;
	}
	
	public int getIdFromUUID(UUID uuid){
		Spiller s = pl.spillere.get(uuid);
		if(s != null){
			return s.getId();
		}
		return -3;
	}
	

	
	public void createPlayer(String name, UUID uuid){
		try {
				pl.sql.query("REPLACE INTO `players` (uuid, name, raid, balance, rank, timeplayed) VALUES ('" + uuid + "', '" + name + "', " + 1 + ", " + 50 + ", " + 1 + ", " + 0F + ")");
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			ResultSet rs = pl.sql.query("SELECT id FROM players WHERE uuid='" + uuid + "'");
			if(rs.next()){
				pl.spillere.put(uuid, new Spiller(uuid, name, rs.getInt("id"), true,50, Rank.BRUKER, 0));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println(org.bukkit.ChatColor.RED + "[Error] Kunne ikke finne ID spiller som ble laget");
		}
	}
	
	public boolean isPlayer(UUID uuid){
		ResultSet rs;
		try {
			rs = pl.sql.query("SELECT * FROM players WHERE uuid='" + uuid + "'");
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	public void addPlayerToSet(UUID uuid, String name){
		try {
			ResultSet rs = pl.sql.query("SELECT * FROM players WHERE uuid='" + uuid + "'");
			if(rs.next()){
				pl.spillere.put(uuid, new Spiller(uuid, name, rs.getInt("id"), rs.getBoolean("raid"), rs.getInt("balance"), pl.getChatHandler().getRankFromInt(rs.getInt("rank")), rs.getInt("timeplayed")));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public PermissionGroup getGroupFromPlayer(Player p){
		PermissionUser user = PermissionsEx.getUser(p);
		for(PermissionGroup group : PermissionsEx.getPermissionManager().getGroupList()){
			if(group.getActiveUsers().contains(user)){
				return group;
			}
		}
		return PermissionsEx.getPermissionManager().getGroup("BRUKER");
	}
	
	public int getIntFromBoolean(boolean b){
		if(b == true){
			return 1;
		} else {
			return 0;
		}
	}
}
