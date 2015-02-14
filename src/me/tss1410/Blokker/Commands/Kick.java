package me.tss1410.Blokker.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Kick implements CommandExecutor {
	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String commandLabel, final String[] args) {
		if (command.getName().equalsIgnoreCase("kick")) {
			if (Sender.hasPermission("Blokker.kick")) {
				if (args.length == 0) {
					Sender.sendMessage(ChatColor.RED
							+ "Feil: Bruk /kick <spiller>");
					return true;
				} else if (args.length == 1) {
					final Player targetPlayer = Bukkit.getPlayer(args[0]);
					targetPlayer.kickPlayer("Du ble kicket av "+ ChatColor.WHITE + Sender.getName()+ ChatColor.GREEN+ " Grunn: Ingen grunn oppgitt. Vi trenger ingen");
					Bukkit.broadcastMessage(ChatColor.DARK_GREEN+ targetPlayer.getName() + " ble sparket ut av "+ Sender.getName());
					Bukkit.broadcastMessage(ChatColor.DARK_GREEN+ "Grunn: Ingen grunn oppgitt. Vi trenger ingen");
					return true;
				} else if (args.length >= 2) {
					final Player targetPlayer = Bukkit.getPlayer(args[0]);
					final StringBuffer me = new StringBuffer();
					for (int i = 1; i < args.length; i++) {
						me.append(args[i] + " ");
					}
					targetPlayer.kickPlayer("Du ble kicket av "+ ChatColor.WHITE + Sender.getName()+ ChatColor.GREEN + " Grunn: "+ me.toString().trim());
					Bukkit.broadcastMessage(ChatColor.DARK_GREEN+ targetPlayer.getName() + " ble sparket ut av "+ Sender.getName());
					Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "Grunn: "+ me.toString().trim());
					return true;
				}
			}
		}
		return false;
	}
}
