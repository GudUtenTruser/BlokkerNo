package me.tss1410.Blokker.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Viktig implements CommandExecutor{
	
	public boolean onCommand(CommandSender Sender, Command command, String commandLabel, String[] args){//Ser ingen feil.. Så dette er et problem..
		if(Sender instanceof Player){


		StringBuffer me = new StringBuffer();
		for(int i=0; i<args.length; i++){
			me.append(args[i] + " ");

		}
		if(Sender.hasPermission("Blokker.viktig")){
			if(command.getName().equalsIgnoreCase("viktig")){
				if(args.length==0){
					Sender.sendMessage(ChatColor.RED + "Feil: Bruk /viktig <melding>");
				} else if(args.length>=1){ 
					Bukkit.broadcastMessage(ChatColor.RED + "[" + ChatColor.DARK_RED + "Viktig" + ChatColor.RED + "] " +ChatColor.WHITE + me);

				}
			}
		}
		}
		return true;
	}
}

