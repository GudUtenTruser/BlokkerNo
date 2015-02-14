package me.tss1410.Blokker.Commands;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;
import me.tss1410.Blokker.Warp.SetWarpThread;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarp implements CommandExecutor{

	public Main pl;

	public SetWarp(Main plugin){
		pl=plugin;
	}

	public boolean onCommand(CommandSender Sender, Command command, String commandLabel, String args[]){
		if(!(Sender instanceof Player)){
			return false; 
		} else {
			if(args.length == 1){
				if(Sender.hasPermission("Blokker.setwarp")){
					final String s = args[0];
					final Player p = (Player) Sender;
					Location loc = p.getLocation();
					final Spiller sp = pl.spillere.get(p.getUniqueId());

					SetWarpThread sht = new SetWarpThread((int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), sp, loc.getWorld().getName(), s, pl);
					try{
						sht.start();
					} catch(Exception e){
						e.printStackTrace();
					}



				} else {
					Sender.sendMessage(ChatColor.RED + "Du har ikke tilgang til å utføre denne kommandoen");
				}
			} else {
				Sender.sendMessage(ChatColor.RED + "Bruk /setwarp <navn>");
			}
		}
		return false;
	}
	

}
