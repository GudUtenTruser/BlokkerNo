package me.tss1410.Blokker.Commands;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Home.HomeData;
import me.tss1410.Blokker.Players.Spiller;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Home implements CommandExecutor{
	
	public Main pl;

	public Home(Main plugin){
		pl=plugin;
	}

	public boolean onCommand(CommandSender Sender, Command command, String commandLabel, String args[]){
		if(!(Sender instanceof Player)){
			return false;
		} else {
			if(!Sender.hasPermission("Blokker.home")){
				Sender.sendMessage(ChatColor.RED + "Du har ikke tilgang til å utføre denne kommandoen");
			}
			if(args.length == 1){
				Spiller s = pl.spillere.get(((Player) Sender).getUniqueId());
				for(HomeData h : pl.homes){
					if(h.getName().equalsIgnoreCase(args[0]) && h.getPlayerID() == s.getId()){
						((Player) Sender).teleport(new Location(Bukkit.getWorld(h.getWorld()), h.getX(), h.getY(), h.getZ()));
						Sender.sendMessage(ChatColor.GREEN + "Poff!");
						return false;
					}
				}
				Sender.sendMessage(ChatColor.RED + "Kunne ikke finne hjemmet");

			} else {
				Sender.sendMessage(ChatColor.RED + "Bruk /home <navn>");
			}
		}

		return false;
	}

}
