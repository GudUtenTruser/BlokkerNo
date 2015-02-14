package me.tss1410.Blokker.Commands;

import me.tss1410.Blokker.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Blokker implements CommandExecutor{
	
	public Main pl;
	
	public Blokker(Main plugin){
		pl=plugin;
	}

	public boolean onCommand(CommandSender Sender, Command command, String commandLabel, String args[]){
		if(args.length == 1){
			if(args[0].equalsIgnoreCase("reload")){
				if(Sender.hasPermission("blokker.reload")){
					pl.getServer().getPluginManager().disablePlugin(pl);
					pl.getServer().getPluginManager().enablePlugin(pl);
				}
			}


		}
		return false;
	}

}
