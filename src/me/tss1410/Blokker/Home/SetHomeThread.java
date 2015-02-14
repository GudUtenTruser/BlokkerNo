package me.tss1410.Blokker.Home;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;

public class SetHomeThread extends Thread{
	

	public int x,y,z;
	public Spiller s;
	public String world,name;
	public Main pl;
	public String message;

	public SetHomeThread(int x, int y, int z, Spiller s, String world, String name, Main plugin){
		this.x = x;
		this.y = y;
		this.z = z;
		this.s = s;
		this.world=world;
		this.name=name;
		pl=plugin;
	}

	public void run(){

		try {

			ResultSet rs = pl.sql.query("SELECT * FROM homes WHERE player=" + s.getId() + " AND name='" + name + "'");
			if(rs.next()){
				pl.sethomemessage.put(s, ChatColor.RED + "Dette hjemmet eksisterer fra før");
				return;
			} else {
				message = ChatColor.GREEN + "Hjem " + name + " satt";
				pl.homes.add(new HomeData(x, y, z, world, name, s.getId(), message));
				System.out.println(pl.homes);
				pl.sql.insert("homes", new String[] {"x", "y", "z", "world", "name", "player"}, new Object[] {x,y,z,world,name,s.getId()});

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		


	}

}
