package me.tss1410.Blokker.Commands;

import java.util.HashMap;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class M implements CommandExecutor {

	public static HashMap<String, String> resend = new HashMap<String, String>();



	public String arrayToString(final String[] array) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 1; i < array.length; i++) {
			sb.append(array[i]);
			sb.append(" ");
		}

		return sb.toString();
	}

	private Player findPlayerByName(final String playerName) {
		final Player player = Bukkit.getServer().getPlayer(playerName);
		return ((player == null) || (!player.isOnline())) ? null : player;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String commandLabel, final String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Bruk /m spiller <tekst>");
			return true;
		} else if (args.length == 1) {
			sender.sendMessage("Du må huske tekst!");
			return true;

		} else if (args.length >= 2) {
			final Player msender = (Player) sender;
			final String[] message = args;
			final Player reciver = findPlayerByName(message[0]);
			if (msender.getName().equals(reciver.getName())|| (reciver == null)) {
				msender.sendMessage(ChatColor.RED+ "Kan ikke sende meldinger til denne spilleren!");
				return true;
			}
			resend.put(reciver.getName(), msender.getName());
			sender.sendMessage(ChatColor.DARK_GREEN + "Fra ["+ msender.getName() + "] til [" + reciver.getName() + "] "+ ChatColor.GREEN + arrayToString(message));

			reciver.sendMessage(ChatColor.DARK_GREEN + "Til ["+ reciver.getName() + "] fra [" + msender.getName() + "] "+ ChatColor.GREEN + arrayToString(message));



		}
		return true;
	}
}