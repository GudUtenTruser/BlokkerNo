package me.tss1410.Blokker.Commands;

import java.util.HashSet;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Warp.WarpData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Warps implements CommandExecutor{

	public Main pl;

	public Warps(Main plugin){
		pl=plugin;
	}

	public boolean onCommand(CommandSender Sender, Command command, String commandLabel, String args[]){
		if(!(Sender instanceof Player)){
			return false;
		} else {
			if(!Sender.hasPermission("Blokker.warps")){
				Sender.sendMessage(ChatColor.RED + "Du har ikke tilgang til å utføre denne kommandoen");
			}
			if(args.length == 0){
				HashSet<WarpData> warps = new HashSet<WarpData>();
				for(WarpData h : pl.warps){
					warps.add(h);
				}
				if(warps.isEmpty()){
					Sender.sendMessage(ChatColor.RED + "Det finnes ingen warps");
					return false;
				}
				for(WarpData h : warps){
					Sender.sendMessage(ChatColor.GREEN + h.getName());
				}
			}
		}

		return false;
	}
	
}
