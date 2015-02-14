package me.tss1410.Blokker.Home;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;

import org.bukkit.ChatColor;

public class DelHomeThread extends Thread{
	


	public Spiller s;
	public String world,name;
	public Main pl;

	public DelHomeThread(Spiller s, String world, String name, Main plugin){

		this.s = s;
		this.world=world;
		this.name=name;
		pl=plugin;
	}

	public void run(){
		for(HomeData h : pl.homes){
			if((h.getName().equalsIgnoreCase(name)) && h.getPlayerID() == s.getId()){
				pl.deletedhomes.add(h);
				try {
					ResultSet rs = pl.sql.query("SELECT * FROM homes WHERE player=" + s.getId() + " AND name='" + name + "'");
					if(rs.next()){
						pl.sql.query("DELETE FROM homes WHERE name='" + name + "' AND player=" + s.getId());
						for(HomeData ho : pl.deletedhomes){
							if(ho == h){
								s.sendMessage(ChatColor.RED + "Hjemmet er slettet");
								pl.homes.remove(h);
								return;
							}
						}
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
