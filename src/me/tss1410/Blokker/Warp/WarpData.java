package me.tss1410.Blokker.Warp;

public class WarpData {
	
	
	public String name;
	public String world;
	public int x,y,z;
	
	public WarpData(String name, String world, int x, int y, int z){
		this.name = name;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String getName(){
		return name;
	}
	
	public String getWorld(){
		return world;
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
	
	//////////////////////////////////////////////////////////////
	
	

}
