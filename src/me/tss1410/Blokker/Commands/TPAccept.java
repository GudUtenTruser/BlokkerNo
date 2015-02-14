package me.tss1410.Blokker.Commands;

import me.tss1410.Blokker.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPAccept implements CommandExecutor {

	public Main pl;

	public TPAccept(Main plugin) {
		pl = plugin;
	}

	public boolean onCommand(CommandSender Sender, Command command,
			String commandLabel, String args[]) {
		if (Sender instanceof Player) {
			if (args.length == 0) {
				if (command.getName().equalsIgnoreCase("tpaccept")) {
					if (pl.tpa.containsKey(Sender.getName())) {
						Player p = (Player) Bukkit.getPlayer(pl.tpa.get(Sender.getName()));
						p.teleport(((Player) Sender).getLocation());
						p.sendMessage(ChatColor.GREEN + "poff");
						pl.tpa.remove(Sender.getName());
						Sender.sendMessage(ChatColor.GREEN + p.getName()+ " ble teleportert til deg");
					} else {
						Sender.sendMessage(ChatColor.RED+ "Du har ingen teleport forespørsel");
					}

				}
			
			}
		}
		return false;

	}

}
