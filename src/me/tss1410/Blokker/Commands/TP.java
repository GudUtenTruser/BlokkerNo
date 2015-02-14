package me.tss1410.Blokker.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TP implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String commandLabel, final String[] args) {
		if (Sender instanceof Player) {
			final Player p = (Player) Sender;
			final Player targetPlayer = p.getServer().getPlayer(args[0]);

			if (p.hasPermission("Blokker.tp")) {
				if (command.getName().equalsIgnoreCase("tp")) {
					if (args.length == 0) {
						p.sendMessage(ChatColor.RED + "/tp <spiller>");
					} else if (args.length == 1) {
						final Location targetPlayerLocation = targetPlayer.getLocation();
						p.teleport(targetPlayerLocation);
						p.sendMessage(ChatColor.GOLD+ "[Alarm] Du ble teleportert til "+ targetPlayer.getName() + ".");

					} else if (args.length == 3) {
						final double x = Double.parseDouble(args[0]);
						final double z = Double.parseDouble(args[2]);
						final double y = Double.parseDouble(args[1]);
						p.teleport(new Location(p.getWorld(), x, y, z));

					} else if (args.length == 4) {
						Player tp = Bukkit.getServer().getPlayer(args[0]);
						final double x = Double.parseDouble(args[1]);
						final double z = Double.parseDouble(args[3]);
						final double y = Double.parseDouble(args[2]);
						tp.teleport(new Location(tp.getWorld(), x, y, z));
					}
				}
			}
		} else {
			Sender.sendMessage(ChatColor.RED+ "Du er ikke en in game spiller");
			return true;
		}

		return true;
	}
}
