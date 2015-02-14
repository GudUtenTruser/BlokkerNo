package me.tss1410.Blokker.Warp;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;

public class SetWarpThread extends Thread{
	

	public int x,y,z;
	public Spiller s;
	public String world,name;
	public Main pl;
	public String message;

	public SetWarpThread(int x, int y, int z, Spiller s, String world, String name, Main plugin){
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

			ResultSet rs = pl.sql.query("SELECT * FROM warps WHERE name='" + name + "'");
			if(rs.next()){
				s.sendMessage(ChatColor.RED + "Dette warpet finnes fra før");
				return;
			} else {
				s.sendMessage(ChatColor.GREEN + "Warp " + name + "satt");
				pl.warps.add(new WarpData(name, world, x,y,z));
				pl.sql.insert("warps", new String[] {"x", "y", "z", "world", "name"}, new Object[] {x,y,z,world,name});

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		


	}

}
