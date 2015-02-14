package me.tss1410.Blokker.Commands;

import me.tss1410.Blokker.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Back implements CommandExecutor {

	public Main pl;

	public Back(Main plugin) {
		pl = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd,String commandLabel, String[] args) {
		if(sender.hasPermission("Blokker.back") == false){
			sender.sendMessage(ChatColor.RED + "Du har ikke tilgang til å utføre denne kommandoen");
			return false;
		}
		if (commandLabel.equalsIgnoreCase("back")) {
			if (pl.deaths.containsKey(sender.getName())) {
				Player p = (Player) sender;
				p.teleport((org.bukkit.Location) pl.deaths.get(p.getName()));
				p.sendMessage(ChatColor.GREEN + "Poff!");
			} else {
				sender.sendMessage(ChatColor.RED+ "Kan ikke finne din siste posisjon");
			}

		}

		return false;
	}

}
