package me.tss1410.Blokker.Players;

import java.util.UUID;

import org.bukkit.Bukkit;
import me.tss1410.Blokker.Chat.*;

public class Spiller {
	
	UUID uuid;
	int id;
	String name;
	boolean raid;
	int money;
	Rank rank;
	int timeplayed;
	
	public Spiller(UUID uuid, String name, int id, boolean raid, int money, Rank rank, int timeplayed){
		this.uuid = uuid;
		this.id = id;
		this.name = name;
		this.raid = raid;
		this.money = money;
		this.rank = rank;
		this.timeplayed=timeplayed;
	}
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public UUID getUUID(){
		return uuid;
	}
	
	public boolean getRaid(){
		return raid;
	}
	
	public int getBalance(){
		return money;
	}
	
	public Rank getRank(){
		return rank;
	}
	
	public  int getTimePlayed(){
		return timeplayed;
	}
	
	//////////////////////////////////////////////////////////////////
	
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setUUID(UUID uuid){
		this.uuid = uuid;
	}
	
	public void setRaid(boolean raid){
		this.raid = raid;
	}
	
	public void setBalance(int money){
		this.money = money;
	}
	
	public void setRank(Rank rank){
		this.rank = rank;
	}
	
	public void setTimePlayed(int timeplayed){
		this.timeplayed=timeplayed;
	}
	
	/////////////////////////////////////////////////////////////
	
	public void sendMessage(String s){
		Bukkit.getPlayer(getName()).sendMessage(s);
	}
	

}
