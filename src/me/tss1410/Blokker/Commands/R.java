package me.tss1410.Blokker.Commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class R implements CommandExecutor {



	public String arrayToString(final String[] array) {
		final StringBuilder sb = new StringBuilder();
		for (final String element : array) {
			sb.append(element);
			sb.append(" ");
		}
		return sb.toString();
	}

	private Player findPlayerByName(final String playerName) {
		final Player player = Bukkit.getServer().getPlayer(playerName);
		return ((player == null) || (!player.isOnline())) ? null : player;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd,
			final String label, final String[] args) {
		if (sender.hasPermission("Blokker.r")) {
			if (cmd.getName().equalsIgnoreCase("r")) {
				final Player msender = (Player) sender;
				final String[] message = args;
				if (M.resend.containsKey(msender.getName())) {
					final Player reciver = findPlayerByName(M.resend.get(msender.getName()));
					sender.sendMessage(ChatColor.DARK_GREEN + "Fra ["+ msender.getName() + "] til [" + reciver.getName()+ "] " + ChatColor.GREEN + arrayToString(message));

					reciver.sendMessage(ChatColor.DARK_GREEN + "Til ["+ reciver.getName() + "] fra [" + msender.getName()+ "] " + ChatColor.GREEN + arrayToString(message));

					

					
				} else {
					sender.sendMessage(ChatColor.RED + "Hvem skal du svare?");
				}
			}
		}
		return false;
	}

}
