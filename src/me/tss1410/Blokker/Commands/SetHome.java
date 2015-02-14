package me.tss1410.Blokker.Commands;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Home.HomeData;
import me.tss1410.Blokker.Home.SetHomeThread;
import me.tss1410.Blokker.Players.Spiller;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHome implements CommandExecutor{

	public Main pl;

	public SetHome(Main plugin){
		pl=plugin;
	}

	public boolean onCommand(CommandSender Sender, Command command, String commandLabel, String args[]){
		if(!(Sender instanceof Player)){
			return false; 
		} else {
			if(args.length == 1){
				if(Sender.hasPermission("Blokker.sethome")){
					final String s = args[0];
					final Player p = (Player) Sender;
					Location loc = p.getLocation();
					final Spiller sp = pl.spillere.get(p.getUniqueId());

					SetHomeThread sht = new SetHomeThread((int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), sp, loc.getWorld().getName(), s, pl);
					try{
						sht.start();
					} catch(Exception e){
						e.printStackTrace();
					}
					 pl.getServer().getScheduler().runTaskLater(pl, new Runnable() {
							public void run() {
								sendMessage(s, sp, p);
								
							}
						}, 1 * 3L);



				} else {
					Sender.sendMessage(ChatColor.RED + "Du har ikke tilgang til å utføre denne kommandoen");
				}
			} else {
				Sender.sendMessage(ChatColor.RED + "Bruk /sethome <navn>");
			}
		}
		return false;
	}
	
	public void sendMessage(String s, Spiller sp, Player p){
		for(HomeData h : pl.homes){
			
			if((h.getName().equalsIgnoreCase(s)) && h.getPlayerID() == sp.getId()){
				if(pl.sethomemessage.containsKey(sp)){
					p.sendMessage(pl.sethomemessage.get(sp));
					pl.sethomemessage.remove(sp);
				} else {
				p.sendMessage(h.getMessage());
				}
			}
		}
	}

}
