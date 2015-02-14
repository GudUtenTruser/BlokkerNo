package me.tss1410.Blokker.PVPRaid;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class BlokkHandler {
	
	public Main pl;
	
	public BlokkHandler(Main plugin){
		pl=plugin;
	}
	

	public void placeBlock(Block b, Player p){
		if(b.getWorld().getName().equalsIgnoreCase("Survival")){ // SPØR OM DETTE!!!!!

			int x = b.getX();
			int y = b.getY();
			int z = b.getZ();
			String world = b.getWorld().getName();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			try {
				pl.sql.query("REPLACE INTO blockprotection (x,y,z,world,material,date,placer) VALUES (" + x + ", " + y + ", " + z + ", '" + world +  "' , '" + b.getType().toString() + "', '" + formatter.format(cal.getTime()) + "', '" + pl.getPlayerHandler().getIdFromUUID(p.getUniqueId()) + "')");
			} catch (SQLException e1) {
				e1.printStackTrace();
				p.sendMessage(ChatColor.RED + "[Error] "+ "Det gikk noe galt med plassering av denne blokken. Si i fra til en mod/admin");
				b.breakNaturally();
			}
		}
	}

	public int getOwnerId(Block b){
		try {
			ResultSet rs = pl.sql.query("SELECT placer FROM blockprotection WHERE x=" + b.getX() + " AND y=" + b.getY() + " AND z=" + b.getZ() + " AND world='" + b.getWorld().getName() + "'");
			if(rs.next()){
				for(Spiller s : pl.spillere.values()){
					if(s.getId() == rs.getInt("placer")){
						return s.getId();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	
	@SuppressWarnings("deprecation")
	public boolean denyHoppers(Block h, Player p){
		
		Spiller s = pl.spillere.get(p.getUniqueId());
		Block b = h.getRelative(BlockFace.UP);
		if(!(pl.getBlockHandler().getOwnerId(b) == s.getId())){
			switch(b.getType()){
			case CHEST:
			case HOPPER:
			case FURNACE:
			case BURNING_FURNACE:
			case TRAPPED_CHEST:
			case DROPPER:
			case DISPENSER:
			case BEACON:
			case BREWING_STAND:
			case LOCKED_CHEST:
			case HOPPER_MINECART:

				return true;

			default:
				return false;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public boolean denyInteract(Block b, Player p){
		Spiller s = pl.spillere.get(p.getUniqueId());
		switch(b.getType()){
		case WOODEN_DOOR:
		case IRON_DOOR:
		case BIRCH_DOOR:
		case ACACIA_DOOR:
		case SPRUCE_DOOR:
		case DARK_OAK_DOOR:
		case JUNGLE_DOOR:
		case CHEST:
		case HOPPER:
		case FURNACE:
		case BURNING_FURNACE:
		case TRAPPED_CHEST:
		case DROPPER:
		case DISPENSER:
		case BEACON:
		case BREWING_STAND:
		case LOCKED_CHEST:
		case HOPPER_MINECART:
		case LEVER:
		case WOOD_BUTTON:
		case STONE_BUTTON:
		

			if((pl.getBlockHandler().getOwnerId(b) == s.getId()) == false){
				if(!pl.isRaiding){
					return true;
				}
			}
			
		default:
			break;
	}
		return false;
	}
	
}
