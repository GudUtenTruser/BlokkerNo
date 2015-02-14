package me.tss1410.Blokker.Home;

import org.bukkit.ChatColor;

public class HomeData {
	
	public int x,y,z,spillerid;
	public String world,name;
	public String message = ChatColor.RED + "Du har allerede et hjem med dette navnet";
	
	
	public HomeData(int x, int y, int z, String world, String name, int spillerid, String message){
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
		this.name = name;
		this.spillerid=spillerid;
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getZ(){
		return z;
	}
	
	public String getWorld(){
		return world;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPlayerID(){
		return spillerid;
	}
	
	//////////////////////////////////////////////////////
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setZ(int z){
		this.z = z;
	}
	
	public void setWorld(String world){
		this.world = world;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setPlayerID(int spillerid){
		this.spillerid = spillerid;
	}
	


}
