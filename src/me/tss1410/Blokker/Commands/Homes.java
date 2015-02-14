package me.tss1410.Blokker.Commands;

import java.util.HashSet;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Home.HomeData;
import me.tss1410.Blokker.Players.Spiller;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Homes implements CommandExecutor{

	public Main pl;

	public Homes(Main plugin){
		pl=plugin;
	}

	public boolean onCommand(CommandSender Sender, Command command, String commandLabel, String args[]){
		if(!(Sender instanceof Player)){
			return false;
		} else {
			if(!Sender.hasPermission("Blokker.homes")){
				Sender.sendMessage(ChatColor.RED + "Du har ikke tilgang til å utføre denne kommandoen");
			}
			if(args.length == 0){
				HashSet<HomeData> homes = new HashSet<HomeData>();
				Spiller s = pl.spillere.get(((Player) Sender).getUniqueId());
				for(HomeData h : pl.homes){
					if(h.getPlayerID() == s.getId()){
						homes.add(h);
					}

				}
				if(homes.isEmpty()){
					Sender.sendMessage(ChatColor.RED + "Du har ingen hjem");
					return false;
				}
				for(HomeData h : homes){
					Sender.sendMessage(ChatColor.GREEN + h.getName());
				}
			}
		}

		return false;
	}

}
