package me.tss1410.Blokker.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class K implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String commandLabel, final String[] args) {
		if (Sender instanceof Player) {
			final Player p = (Player) Sender;
			if (p.hasPermission("Blokker.k")) {
				if (command.getName().equalsIgnoreCase("k")) {
					if (args.length > 1) {
						p.sendMessage(ChatColor.RED
								+ "Prøve /k eller /k <spiller>??");
					}
					if (args.length == 0) {
						if (p.getGameMode().equals(GameMode.CREATIVE)) {
							p.setGameMode(GameMode.SURVIVAL);
							p.sendMessage(ChatColor.GOLD+ "[Alarm] Du skiftet spillermodus til survival");
						} else if (p.getGameMode().equals(GameMode.SURVIVAL)) {
							p.setGameMode(GameMode.CREATIVE);
							p.sendMessage(ChatColor.GOLD+ "[Alarm] Du skiftet spillermodus til kreativ");
							Bukkit.broadcast(ChatColor.GOLD + "[StabAlarm] LAV: "+ ChatColor.WHITE + p.getName() + " skrudde på kreativ", "sh.kick");
						}
					}
					if (args.length == 1) {
						final Player targetPlayer = p.getServer().getPlayer(args[0]);
						if (targetPlayer.getGameMode().equals(GameMode.CREATIVE)) {
							targetPlayer.setGameMode(GameMode.SURVIVAL);
							targetPlayer.sendMessage(ChatColor.GOLD+ "[Alarm] En i stabben skiftet ditt spillermodus til survival");
						} else if (targetPlayer.getGameMode().equals(
								GameMode.SURVIVAL)) {
							targetPlayer.setGameMode(GameMode.CREATIVE);
							targetPlayer.sendMessage(ChatColor.GOLD+ "[Alarm] En i stabben skiftet ditt spillermodus til kreativ");
							Bukkit.broadcast(ChatColor.GOLD+ "[StabAlarm] MIDDELS: " + ChatColor.WHITE+ p.getName() + " skrudde på kreativ til " + targetPlayer.getName(), "sh.kick");
						}
					}
				}
			}
		} else {
			if (args.length == 1) {
				final Player targetPlayer = Bukkit.getServer().getPlayer(
						args[0]);
				if (targetPlayer.getGameMode().equals(GameMode.CREATIVE)) {
					targetPlayer.setGameMode(GameMode.SURVIVAL);
					targetPlayer.sendMessage(ChatColor.GOLD+ "[Alarm] En i stabben skiftet ditt spillermodus til survival");
				} else if (targetPlayer.getGameMode().equals(GameMode.SURVIVAL)) {
					targetPlayer.setGameMode(GameMode.CREATIVE);
					targetPlayer.sendMessage(ChatColor.GOLD+ "[Alarm] En i stabben skiftet ditt spillermodus til kreativ");
				} else {
					Sender.sendMessage(ChatColor.RED + "Du er ikke en spiller");

				}
			}
		}
		return true;
	}
}