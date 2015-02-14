package me.tss1410.Blokker.Commands;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ban implements CommandExecutor{

	private Main pl;

	public Ban(final Main main)
	{
		this.pl = main;
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "Denne kommandoen kan bare brukes av online spillere!");
			return true;
		}

		Player p = (Player) sender;

		if(!p.hasPermission("Blokker.ban")) {
			p.sendMessage(ChatColor.RED + "Du har tilgang til å utføre denne kommandoen");
			return false;
		}

		if(args.length < 1) {
			p.sendMessage(ChatColor.RED + "Bruk /ban <spiller> <grunn>");
		}

		if(args.length > 1){
			StringBuffer me = new StringBuffer();
			for(int i = 1; i<args.length; i++){
				me.append(args[i] + " ");
			}
			for(Spiller s : pl.spillere.values()){
				System.out.println(s.getName());
			}
			

			System.out.println(pl.getPlayerHandler().getUUIDFromName(args[0]) == null);
			System.out.println(pl.getPlayerHandler().getUUIDFromName(args[0]));
			Player tg = (Player) Bukkit.getServer().getPlayer(args[0]);
			Spiller s = pl.spillere.get(pl.getPlayerHandler().getUUIDFromName(tg.getName()));

			if(s != null){
			} else {
				p.sendMessage(ChatColor.RED + "Kunne ikke legge banne spiller. Spilleren er ikke i systemet");
				return false;
			}

			
			if(tg != null){
				pl.sql.insert("bandata", new String[] { "player", "type" , "grunn"}, new Object[] { s.getId(), 1, me});
				tg.kickPlayer(ChatColor.DARK_RED + "Du ble permbannet!" + ChatColor.RED + "\n Grunn: \n" + ChatColor.YELLOW + me);
				
				for(Player p1 : Bukkit.getOnlinePlayers()){
					if(p1.hasPermission("Blokker.ban")){
						p1.sendMessage(ChatColor.GOLD + "[StabAlarm] " + ChatColor.YELLOW + tg.getDisplayName() + ChatColor.GREEN + " ble permbannet av " + ChatColor.YELLOW + p.getDisplayName());
						p1.sendMessage(ChatColor.GOLD + "[StabAlarm] " + ChatColor.YELLOW + "Grunn: " + ChatColor.GREEN + me);
					}
				}
				return false;
			} else {
				pl.sql.insert("bandata", new String[] { "player", "type" , "reason"}, new Object[] { s.getId(), 1, me});
				for(Player p1 : Bukkit.getOnlinePlayers()){
					if(p1.hasPermission("Blokker.ban")){
						p1.sendMessage(ChatColor.GOLD + "[StabAlarm] " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " ble permbannet av " + ChatColor.YELLOW + p.getDisplayName());
						p1.sendMessage(ChatColor.GOLD + "[StabAlarm] " + ChatColor.YELLOW + "Grunn: " + ChatColor.GREEN + me);
					}
				}
			}
			return true;
		}
		return false;
	}

	
}
