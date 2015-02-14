package me.tss1410.Blokker.Players;

import java.util.UUID;

import org.bukkit.inventory.ItemStack;



public class WorkPlayerData {
	
	public ItemStack[] inv;
	public ItemStack[] armor;
	public UUID uuid;
	
	public WorkPlayerData(UUID uuid, ItemStack[] inv, ItemStack[] armor){
		this.uuid = uuid;
		this.inv = inv;
		this.armor = armor;
	}
	
	public ItemStack[] getInventory(){
		return inv;
	}
	
	public ItemStack[] getArmor(){
		return armor;
	}
	
	public UUID getUUID(){
		return uuid;
	}
	
	//////////////////////////////////////////
	
	public void setInventory(ItemStack[] inv){
		this.inv = inv;
	}
	
	public void setArmor(ItemStack[] armor){
		this.armor = armor;
	}
	
	public void setUUID(UUID uuid){
		this.uuid = uuid;
	}

}
