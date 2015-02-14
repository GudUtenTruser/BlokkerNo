package me.tss1410.Blokker.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sitt implements CommandExecutor {

	public boolean onCommand(CommandSender Sender, Command command,
			String commandLabel, String args[]) {

		if (Sender instanceof Player) {
			if (command.getName().equalsIgnoreCase("sitt")) {
				if (Sender.hasPermission("Blokker.sitt")) {
					if (args.length == 1) {
						Player p = (Player) Bukkit.getPlayer(args[0]);
						Player tg = (Player) Sender;
						p.setPassenger(tg);
					}
				}
			}
		}

		return false;

	}

}
