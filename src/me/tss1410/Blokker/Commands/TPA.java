package me.tss1410.Blokker.Commands;

import me.tss1410.Blokker.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TPA implements CommandExecutor {

	public Main pl;

	public TPA(Main plugin) {
		pl = plugin;
	}

	public boolean onCommand(final CommandSender Sender, Command command,String commandLabel, String args[]) {
		if (command.getName().equalsIgnoreCase("tpa")) {
			if (args.length == 1) {
				Player p = (Player) Bukkit.getPlayer(args[0]);
				if (p != null) {
					pl.tpa.put(p.getName(), Sender.getName());
					Sender.sendMessage(ChatColor.GREEN + "Forespørsel sendt!");
					p.sendMessage(ChatColor.DARK_GREEN + Sender.getName()+ " vil teleportere til deg.");
					p.sendMessage(ChatColor.GREEN+ "Sriv /tpaccept for å godta");
					pl.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

								@Override
								public void run() {
									pl.tpa.remove(Sender.getName());
								}

							}, 20 * 30);
				}
			} else {
				Sender.sendMessage(ChatColor.RED + "Bruk /tpa <spiller>");
			}
		}
		return false;
	}

}
