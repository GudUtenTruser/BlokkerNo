package me.tss1410.Blokker.Commands;

import java.sql.SQLException;

import me.tss1410.Blokker.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Unban implements CommandExecutor {

	private final Main pl;

	public Unban(final Main pl) {
		this.pl = pl;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd,
			final String label, final String[] args) {
		if ((cmd.getName().equalsIgnoreCase("unban"))) {
			if (args.length == 1) {
				try {
					if (sender.hasPermission("Blokker.unban")) {
						System.out.println(pl.getPlayerHandler().getIdFromName(args[0]));
						pl.sql.query("DELETE FROM bandata WHERE player=" +
								pl.getPlayerHandler().getIdFromName(args[0]));
						sender.sendMessage(ChatColor.GREEN + "Spiller " + args[0] + " unbannet");
						return true;
					}
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			} else {
				sender.sendMessage(ChatColor.RED + "/unban <spiller>");
			}

		}
		return false;
	}
}
