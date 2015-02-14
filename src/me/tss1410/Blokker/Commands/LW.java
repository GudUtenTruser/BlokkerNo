package me.tss1410.Blokker.Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import me.tss1410.Blokker.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LW implements CommandExecutor {

	private final Main pl;

	public LW(final Main pl) {
		this.pl = pl;
	}

	public int countWarns(final String player) {
		int warnings = 0;
		try {
			final ResultSet PC = pl.sql.query("SELECT COUNT(*) FROM warndata WHERE player = '"+ pl.getPlayerHandler().getIdFromName(player) + "'");
			PC.first();
			warnings = PC.getInt(1);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return warnings;
	}

	@SuppressWarnings("unused")
	public void listWarnings(final String player, final CommandSender player2) {
		if (countWarns(player) > 0) {
			try {
				player2.sendMessage(ChatColor.DARK_GREEN
						+ "----------------Advarsler----------------");
				final ResultSet PC = pl.sql
						.query("SELECT id,player,setter,setdate,reason FROM warndata WHERE player = "+ pl.getPlayerHandler().getIdFromName(player));
				while (PC.next()) {
					final int id = PC.getInt("id");
					final Timestamp setby = PC.getTimestamp("setdate");
					final String time = PC.getString("setter");
					final String reason = PC.getString("reason");
					player2.sendMessage(ChatColor.DARK_GREEN + "Advarsel ID: "+ ChatColor.RESET + id);
					player2.sendMessage(ChatColor.DARK_GREEN+ "Advarsel gitt av: " + ChatColor.RESET+ pl.getPlayerHandler().getNameFromId(PC.getInt("setter")));
					player2.sendMessage(ChatColor.DARK_GREEN+ "Advarsel gitt den: " + ChatColor.RESET + setby);
					player2.sendMessage(ChatColor.DARK_GREEN+ "Grunn for advarsel: " + ChatColor.RESET + reason);
					player2.sendMessage("");
				}
				player2.sendMessage(ChatColor.DARK_GREEN
						+ "----------------SLUTT----------------");
			} catch (final SQLException e) {
				player2.sendMessage(ChatColor.RED
						+ "Kunne ikke hente noen av dine advarsler.");
				e.printStackTrace();
			}
		} else {
			player2.sendMessage("Du har ingen advarsler");
		}
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String label, final String[] args) {
		if (command.getName().equalsIgnoreCase("lw")) {
			if(sender.hasPermission("Blokker.lw")){
				listWarnings(sender.getName(), sender);
			}
			return true;
		}
		return false;
	}


}