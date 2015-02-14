package me.tss1410.Blokker.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class S implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String commandLabel, final String[] args) {
		Player p = (Player) Sender;
		if (Sender.hasPermission("Blokker.s")) {
			if (command.getName().equalsIgnoreCase("s")) {
				if (Sender instanceof Player) {
					final Location pos = p.getWorld()
							.getHighestBlockAt(p.getLocation()).getLocation();
					p.teleport(pos);
					p.sendMessage(ChatColor.GREEN + "Poff!");

				} else {
					Sender.sendMessage(ChatColor.RED
							+ "Du er ikke en in-game spiller");
				}
			}
		}
		return true;
	}
}