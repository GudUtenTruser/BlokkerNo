package me.tss1410.Blokker.Commands;

import me.tss1410.Blokker.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AFK implements CommandExecutor {

	public Main pl;
	
	public AFK(Main plugin){
		pl = plugin;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd,
			final String label, final String[] args) {
		if(sender.hasPermission("Blokker.afk")){
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Du må være en spiller.");
				return true;
			}
			if ((sender instanceof Player)) {
				final String player = sender.getName();
				if (!pl.afkusers.contains(player)) {
					pl.afkusers.add(player);
					Bukkit.broadcastMessage(ChatColor.DARK_RED + player
							+ ChatColor.RED + " er nå AFK.");
					return true;
				} else {
					pl.afkusers.remove(player);
					Bukkit.broadcastMessage(ChatColor.DARK_GREEN + player
							+ ChatColor.GREEN + " er ikke lenger AFK.");
					return true;
				}
			}
		} else {
			
		}
		return false;
	}
}
