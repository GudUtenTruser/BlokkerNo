package me.tss1410.Blokker.Players;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import me.tss1410.Blokker.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerListener implements Listener{

	public Main pl;

	public PlayerListener(Main plugin){
		pl=plugin;
	}
	@EventHandler
	public void onPlayerMove(final PlayerMoveEvent e){
		if(pl.afkusers.contains(e.getPlayer().getName())){
			pl.afkusers.remove(e.getPlayer().getName());
			Bukkit.broadcastMessage(ChatColor.DARK_GREEN + e.getPlayer().getName() + ChatColor.GREEN + " er ikke lenger AFK.");
		}
		if(e.getTo().getY() < -50){
			e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BURP, 1, 1);
			e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
		}
	}




	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent e){
		
		int s = (int) (System.currentTimeMillis() -pl.timeplayed.get(e.getPlayer().getUniqueId()));
		new UpdateTimePlayed(pl, s, e.getPlayer()).start();
		
		e.setQuitMessage(ChatColor.RED + "[-] " + ChatColor.GRAY + e.getPlayer().getName());
		
		if(pl.work.containsKey(e.getPlayer().getUniqueId())){
			e.getPlayer().setGameMode(GameMode.SURVIVAL);
			e.getPlayer().getInventory().setArmorContents(pl.work.get(e.getPlayer().getUniqueId()).getArmor());
			e.getPlayer().getInventory().setContents(pl.work.get(e.getPlayer().getUniqueId()).getInventory());
		}
	}
	
	@EventHandler
	public void onPlayerTeleport(final PlayerTeleportEvent e){
		pl.deaths.put(e.getPlayer(), e.getFrom());
	}
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent e){
		
		pl.timeplayed.put(e.getPlayer().getUniqueId(), (int)System.currentTimeMillis());
		
		if(e.getPlayer().hasPlayedBefore()){
		e.setJoinMessage(ChatColor.GREEN + "[+] " + ChatColor.GRAY + e.getPlayer().getName());
		} else {
			e.setJoinMessage(ChatColor.GREEN + "[+] " + ChatColor.GRAY + e.getPlayer().getName() + " logget inn for første gang, velkommen!");

		}
		
		pl.getChatHandler().sendTitle(e.getPlayer(), 10, 70, 40, ChatColor.RED + "Velkommen til " + "Blokker", "Blokk, blokk, og mere blokk");
		
		if(!pl.getPlayerHandler().isPlayer(e.getPlayer().getUniqueId())){
			pl.getPlayerHandler().createPlayer(e.getPlayer().getName(), e.getPlayer().getUniqueId());
			


		} else {
			if(pl.spillere.containsKey(e.getPlayer().getUniqueId()) == false){

				ResultSet rs;
				try {
					rs = pl.sql.query("SELECT * FROM players WHERE uuid='" + e.getPlayer().getUniqueId() + "'");
					if(rs.next()){
						pl.spillere.put(e.getPlayer().getUniqueId(), new Spiller(e.getPlayer().getUniqueId(), e.getPlayer().getName(), rs.getInt("id"), rs.getBoolean("raid"), rs.getInt("balance"), pl.getChatHandler().getRankFromInt(rs.getInt("rank")), rs.getInt("timeplayed")));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}


			}
		}
		Spiller s = pl.spillere.get(e.getPlayer().getUniqueId());
		if(s.getName().equalsIgnoreCase(e.getPlayer().getName()) == false){
			s.setName(e.getPlayer().getName());
		}
	}
	
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		if(e.getPlayer().hasPermission("Blokker.adminstick")){
			if(e.getPlayer().getItemInHand().getType() == Material.STICK){
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
					e.getClickedBlock().setType(Material.AIR);
				}
			}
		}
	}
	
	
	@EventHandler
	public void onLoginCheckBanned(PlayerLoginEvent e){
		Spiller s = pl.spillere.get(e.getPlayer().getUniqueId());
		if(s != null){
		try {
			ResultSet rs = pl.sql.query("SELECT * FROM bandata WHERE player='" + s.getId() + "'");
			if (rs.first()){
				if(rs.getInt("type") == 0) {

					/*	int time = (int) (ts.getTime() - System.currentTimeMillis());
					int minutter = time / 1000 / 60;
					int timer = time / 1000 / 60 / 60;
					int dager = time / 1000 / 60 / 60 / 24;
					// int totaldager = 0;

					for(int min = minutter; min >= 60; min -= 60){
						timer += 1;
						minutter -= 60;
					}

					for(int tim = timer; tim >= 24; tim -= 24) {
						dager += 1;
						timer -= 24;
					}  */
					Timestamp ts = rs.getTimestamp("until");				
					if (ts.before(new Timestamp(new Date().getTime()))){
						pl.sql.query("DELETE FROM bandata WHERE player='" + s.getId() + "'");
						return;
					}

					e.disallow(Result.KICK_BANNED, ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "Du er tempbannet! \n" + 
							ChatColor.RED +  "\n Unbannes: \n" + ChatColor.YELLOW + ts + 
							ChatColor.RED + "\n Grunn: \n" + ChatColor.YELLOW + rs.getString("grunn"));
					return;

				}

				if(rs.getInt("type") == 1){
					e.disallow(Result.KICK_BANNED, ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "Du er permbannet! \n" +  
							ChatColor.RED + "\n Grunn: \n" + ChatColor.YELLOW + rs.getString("grunn"));
					return;
				} else if(rs.getInt("type") == 2){
					e.disallow(Result.KICK_BANNED, ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "IP-Adressen din er bannet! \n" +  
							ChatColor.RED + "\n Grunn: \n" + ChatColor.YELLOW + rs.getString("grunn"));
					return;
				} else {
					return;
				}
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}
		}
	}
}
