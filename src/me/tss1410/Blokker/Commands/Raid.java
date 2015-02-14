
package me.tss1410.Blokker.Commands;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Raid implements CommandExecutor{
	
	public Main pl;
	
	public Raid(Main plugin){
		pl=plugin;
	}
	
	public boolean onCommand(CommandSender Sender, Command command, String commandLabel, String args[]){
		if(Sender instanceof Player){
			if(args.length == 0){
				if(Sender.hasPermission("Blokker.raid.info")){
					Spiller s = pl.spillere.get(((Player) Sender).getUniqueId());
					if(s.getRaid() == true){
						s.sendMessage(ChatColor.GREEN + "Du har raid på");
					} else {
						s.sendMessage(ChatColor.GREEN + "Du har raid av");
					}
				} else {
					Sender.sendMessage(pl.noPerm);
				}
			} else if(args.length == 1){
				if(args[0].equalsIgnoreCase("toggle")){
					if(Sender.hasPermission("Blokker.raid.toggle")){
						Spiller s = pl.spillere.get(((Player) Sender).getUniqueId());
						if(s.getRaid() == true){
							s.setRaid(false);
							s.sendMessage(ChatColor.GREEN + "Du skrudde raid av");
						} else {
							s.setRaid(true);
							s.sendMessage(ChatColor.GREEN + "Du skrudde raid på");

						}

					}
				} else {
					Sender.sendMessage(pl.noPerm);
				}
			}
		}
		return false;
	}

}
