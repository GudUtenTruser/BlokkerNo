package me.tss1410.Blokker.Players;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.tss1410.Blokker.Main;

import org.bukkit.entity.Player;

public class UpdateTimePlayed extends Thread{
	
	public Main pl;
	public int s;
	public Player player;
	
	
	public UpdateTimePlayed(Main plugin, int s, Player player){
		this.pl = plugin;
		this.s = s;
		this.player=player;
	}
	
	public void run(){
		ResultSet rs;
		try {
			rs = pl.sql.query("SELECT timeplayed FROM players WHERE uuid='" + player.getUniqueId() + "'");
			if(rs.next()){
				int before = rs.getInt("timeplayed");
				int after = (before + s);
				pl.sql.query("UPDATE players SET timeplayed=" + after + " WHERE uuid='" + player.getUniqueId() + "'");
				pl.spillere.get(player.getUniqueId()).setTimePlayed(after);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}

}
