package me.tss1410.Blokker.Warp;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;

import org.bukkit.ChatColor;

public class DelWarpThread extends Thread{
	


	public Spiller s;
	public String name;
	public Main pl;

	public DelWarpThread(Spiller s, String name, Main plugin){

		this.s = s;
		this.name=name;
		pl=plugin;
	}

	public void run(){
		for(WarpData h : pl.warps){
			if((h.getName().equalsIgnoreCase(name))){
				try {
					ResultSet rs = pl.sql.query("SELECT * FROM warps WHERE name='" + name + "'");
					if(rs.next()){
						pl.sql.query("DELETE FROM warps WHERE name='" + name + "'");
						pl.warps.remove(h);
						s.sendMessage(ChatColor.GREEN + "Warp er slettet");
						return;
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		s.sendMessage(ChatColor.RED + "Kunne ikke finne hjemmet");
		
	}
}
