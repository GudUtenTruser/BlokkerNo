package me.tss1410.Blokker.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class H implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String commandLabel, final String[] args) {
		final StringBuffer me = new StringBuffer();
		for (final String arg : args) {
			me.append(arg + " ");
		}
		if (sender.hasPermission("Blokker.h")) { 
			if (command.getName().equalsIgnoreCase("h")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "/h <tekst>");
				} else if (args.length >= 1) {
					Player p = (Player) sender;
					Bukkit.broadcastMessage(ChatColor.DARK_GREEN
							+ "["
							+ ChatColor.GREEN
							+ "Kjøp/Salg"
							+ ChatColor.DARK_GREEN
							+ "] "
							+ ChatColor.DARK_GRAY
							+ "("
							+ ChatColor.GRAY
							+ p.getWorld().getName()
									.replaceAll("world", "Normal")
							+ ChatColor.DARK_GRAY + ") " + ChatColor.YELLOW
							+ sender.getName() + ChatColor.WHITE + ": " + me);
				}
			}
		}
		return true;
	}
}