package me.tss1410.Blokker.PVPRaid;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlokkListener implements Listener{

	public Main pl;

	public BlokkListener(Main plugin){
		pl=plugin;
	}

	@EventHandler
	public void onBlockPlace(final BlockPlaceEvent e){
		if(e.getBlock().getWorld().getName().equalsIgnoreCase("Survival")){
			if(e.getBlock().getType() == Material.HOPPER){
				if(pl.getBlockHandler().denyHoppers(e.getBlockPlaced(), e.getPlayer())){
					e.getBlockPlaced().breakNaturally();
					e.getPlayer().sendMessage(ChatColor.RED + "Du kan ikke plassere denne hopperen her");
					return;
				}
			}

			if(e.getBlock().getType() == Material.WOODEN_DOOR ||
					e.getBlock().getType() == Material.IRON_DOOR ||
					e.getBlock().getType() == Material.BIRCH_DOOR ||
					e.getBlock().getType() == Material.ACACIA_DOOR ||
					e.getBlock().getType() == Material.SPRUCE_DOOR ||
					e.getBlock().getType() == Material.DARK_OAK_DOOR ||
					e.getBlock().getType() == Material.JUNGLE_DOOR){
				Block b = e.getBlock().getRelative(BlockFace.UP);
				pl.getBlockHandler().placeBlock(b, e.getPlayer());
			}

			pl.getBlockHandler().placeBlock(e.getBlock(), e.getPlayer());
		}
	}


	@EventHandler
	public void onBlockBreak(final BlockBreakEvent e){
		if(!(e.getBlock().getWorld().getName().equalsIgnoreCase("Survival"))){
			return;
		}

		try {
			ResultSet rs = pl.sql.query("SELECT * FROM `blockprotection` WHERE x=" + e.getBlock().getX() + " AND y=" + e.getBlock().getY() + " AND z=" + e.getBlock().getZ() + " AND world='" + e.getBlock().getWorld().getName() + "'");
			while(rs.next()){
				UUID uuid = pl.getPlayerHandler().getUUIDFromId(rs.getInt("placer"));
				if(uuid.equals(e.getPlayer().getUniqueId())){
					return;
				} else {
					if(!pl.isRaiding){
						e.getPlayer().sendMessage(ChatColor.RED + pl.getPlayerHandler().getNameFromUUID(uuid) + " eier denne blokken!");
						e.setCancelled(true);

					} else {
						Spiller s = pl.spillere.get(uuid);
						if(s.getRaid() == false){
							e.getPlayer().sendMessage(ChatColor.RED + s.getName() + " er ikke med på dette raidet");
						}
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent e){
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase("Survival")){
		if(pl.getBlockHandler().denyInteract(e.getClickedBlock(), e.getPlayer())){
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.RED + "Du har ikke tilgang til denne");
		}
	}
	}

}





