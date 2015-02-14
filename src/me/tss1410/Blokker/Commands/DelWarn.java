package me.tss1410.Blokker.Commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.tss1410.Blokker.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelWarn implements CommandExecutor {

	public Main pl;

	public DelWarn(Main plugin) {
		pl = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String commandLabel, final String args[]) {
		if (Sender instanceof Player) {
			final Player p = (Player) Sender;
			if (p.hasPermission("Blokker.delwarn")) {
				if (command.getName().equalsIgnoreCase("delwarn")) {
					if (args.length == 1) {
						try {
							final ResultSet PC = pl.sql
									.query("SELECT id FROM warndata WHERE id='"
											+ args[0] + "'");
							if (PC.next()) {
								pl.sql.query("DELETE FROM warndata WHERE id='"
										+ args[0] + "'");
								p.sendMessage(ChatColor.RED + "Warn id: "
										+ args[0] + " er slettet");
							} else {
								p.sendMessage(ChatColor.RED
										+ "Warn id finnes ikke");
							}

						} catch (final SQLException e) {
							p.sendMessage(ChatColor.RED + "Noe gikk galt");
							e.printStackTrace();
						}
					}
				}
			}
		}
		return false;
	}
}
