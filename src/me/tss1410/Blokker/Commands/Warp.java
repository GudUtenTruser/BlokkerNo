package me.tss1410.Blokker.Commands;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Warp.WarpData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Warp implements CommandExecutor{
	
	public Main pl;

	public Warp(Main plugin){
		pl=plugin;
	}

	public boolean onCommand(CommandSender Sender, Command command, String commandLabel, String args[]){
		if(!(Sender instanceof Player)){
			return false;
		} else {
			if(!Sender.hasPermission("Blokker.warp")){
				Sender.sendMessage(ChatColor.RED + "Du har ikke tilgang til å utføre denne kommandoen");
			}
			if(args.length == 1){
				for(WarpData h : pl.warps){
					if(h.getName().equalsIgnoreCase(args[0])){
						((Player) Sender).teleport(new Location(Bukkit.getWorld(h.getWorld()), h.getX(), h.getY(), h.getZ()));
						Sender.sendMessage(ChatColor.GREEN + "Poff!");
						return false;
					}
				}
				Sender.sendMessage(ChatColor.RED + "Kunne ikke finne warpet");
			} else {
				Sender.sendMessage(ChatColor.RED + "Bruk /warp <navn>");
			}
		}

		return false;
	}

}



