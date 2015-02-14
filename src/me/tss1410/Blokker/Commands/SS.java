package me.tss1410.Blokker.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SS implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String CommandLabel, final String[] args) {


		if (!(Sender.hasPermission("Blokker.ss"))) {
			Sender.sendMessage(ChatColor.RED
					+ "Du har ikke tilgang til å utføre denne kommandoen");
		}
		if (command.getName().equalsIgnoreCase("ss")) {
			if (args.length >= 0) {
				final StringBuffer msg = new StringBuffer();
				for (final String arg : args) {
					msg.append(arg + " ");
				}

				for (Player b : Bukkit.getOnlinePlayers()) {
					if (b.hasPermission("Blokker.ss")) {
						b.sendMessage(ChatColor.GOLD + "[Stabsamtale] " + ChatColor.RED + ((Player) Sender).getName()+ ": " + ChatColor.GRAY + msg.toString());

					}
				}
			} else {
				Sender.sendMessage(ChatColor.RED + "Feil: Bruk /ss <melding>");
			}
		}
		return false;
	}
}

