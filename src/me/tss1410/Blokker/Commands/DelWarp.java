package me.tss1410.Blokker.Commands;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;
import me.tss1410.Blokker.Warp.DelWarpThread;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelWarp implements CommandExecutor{

	public Main pl;

	public DelWarp(Main plugin){
		pl=plugin;
	}

	public boolean onCommand(CommandSender Sender, Command command, String commandLabel, String args[]){
		if(!(Sender instanceof Player)){
			return false; 
		} else {
			if(args.length == 1){
				if(Sender.hasPermission("Blokker.delwarp")){
					final String s = args[0];
					final Player p = (Player) Sender;
					final Spiller sp = pl.spillere.get(p.getUniqueId());
					DelWarpThread sht = new DelWarpThread(sp, s, pl);
					sht.start();
				} else {
					Sender.sendMessage(ChatColor.RED + "Du har ikke tilgang til å utføre denne kommandoen");
				}
			} else {
				Sender.sendMessage(ChatColor.RED + "Bruk /delwarp <navn>");
			}
		}
		return false;
	}
	
}
