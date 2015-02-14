package me.tss1410.Blokker.Commands;

import java.sql.Timestamp;
import java.util.Calendar;

import me.tss1410.Blokker.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Tempban  implements CommandExecutor
{
	private Main main;

	public Tempban(final Main main)
	{
		this.main = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "Denne kommandoen kan bare brukes av online spillere!");
			return true;
		}
		
		Player p1 = (Player) sender;

		if(!p1.hasPermission("Blokker.tempban")) {
			p1.sendMessage(ChatColor.RED + "Du har ikke tilgang til å utføre denne kommandoen");
			return false;
		}

		if(args.length < 3) {
			p1.sendMessage(ChatColor.GOLD + "                  Tidsformat:");
			p1.sendMessage(ChatColor.GOLD + "m" + ChatColor.YELLOW + " - Minutter spilleren skal bannes (10m f.eks).");
			p1.sendMessage(ChatColor.GOLD + "h" + ChatColor.YELLOW + " - Timer spilleren skal bannes (5h f.eks).");
			p1.sendMessage(ChatColor.GOLD + "d"  + ChatColor.YELLOW + " - Dager spilleren skal bannes (1d f.eks).");
			p1.sendMessage("");
			p1.sendMessage(ChatColor.RED + "Skriv: /tempban <spiller> <tid> <grunn>");
			return false;
		}


		if(main.spillere.containsKey(main.getPlayerHandler().getUUIDFromName(args[0]))){
		} else {
			p1.sendMessage(ChatColor.RED + "Feil! Denne spilleren er ikke registrert i databasen, utestengelsen kunne ikke opprettes.");
			return false;
		}

		StringBuffer me = new StringBuffer();
		for(int i = 2; i<args.length; i++){
			me.append(args[i] + " ");
		}

		if(me.toString().contains("'")) {
			p1.sendMessage(ChatColor.RED + "Feil! Grunnen kan ikke inneholde '");
			return true;
		}

		Player tg = (Player) Bukkit.getServer().getPlayer(args[0]);

		if (args.length >= 3){
			if(args[1].contains("m")){
				String s = args[1];
				int i = Integer.parseInt(s.split("m")[0]);
				Calendar d = Calendar.getInstance();

				d.add(Calendar.MINUTE, (i));
				Timestamp t = new Timestamp( d.getTimeInMillis());

				main.sql.insert("bandata", new String[] { "player", "type" , "grunn", "until"}, new Object[] { main.getPlayerHandler().getIdFromName(args[0]), 0, me, t.toString()});
				if(tg != null){
					tg.kickPlayer(ChatColor.DARK_RED + "Du ble tempbannet!" + ChatColor.RED + "\n Grunn: \n" + ChatColor.YELLOW + me + ChatColor.DARK_RED + "\n Tid: \n" + ChatColor.YELLOW + i + " minutter!");
					for(Player all : Bukkit.getOnlinePlayers()){
						if(all.hasPermission("Blokker.tempban.melding")){
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.YELLOW + tg.getDisplayName() + ChatColor.RED + " ble tempbannet av " + ChatColor.YELLOW + p1.getDisplayName() + ChatColor.RED + ".");
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.RED + "Tid: " + ChatColor.YELLOW + i + " minutt(er).");
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.RED + "Grunn: " + ChatColor.YELLOW + me);
						}
					}
				} else {
					for(Player all : Bukkit.getOnlinePlayers()){
						if(all.hasPermission("Blokker.tempban.melding")){
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.YELLOW + args[0] + ChatColor.RED + " ble tempbannet av " + ChatColor.YELLOW + p1.getDisplayName() + ChatColor.RED + ".");
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.RED + "Tid: " + ChatColor.YELLOW + i + " minutt(er).");
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.RED + "Grunn: " + ChatColor.YELLOW + me);
						}
					}
				}
			
			} else if(args[1].contains("h")){
				String s = args[1];
				int i = Integer.parseInt(s.split("h")[0]);
				Calendar d = Calendar.getInstance();

				d.add(Calendar.HOUR, (i));
				Timestamp t = new Timestamp( d.getTimeInMillis());

				main.sql.insert("bandata", new String[] { "player", "type" , "grunn", "until"}, new Object[] { main.getPlayerHandler().getIdFromName(args[0]), 0, me, t.toString()});
				if(tg != null){
					tg.kickPlayer(ChatColor.DARK_RED + "Du ble tempbannet! Grunn: \n" + ChatColor.YELLOW + me + ChatColor.DARK_RED + "\n Tid: \n" + ChatColor.YELLOW + i + " time(r)!");
					for(Player all : Bukkit.getOnlinePlayers()){
						if(all.hasPermission("Blokker.tempban.melding")){
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.YELLOW + tg.getDisplayName() + ChatColor.RED + " ble tempbannet av " + ChatColor.YELLOW + p1.getDisplayName() + ChatColor.RED + ".");
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.RED + "Tid: " + ChatColor.YELLOW + i + " time(r).");
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.RED + "Grunn: " + ChatColor.YELLOW + me);
						}
					}
				} else {
					for(Player all : Bukkit.getOnlinePlayers()){
						if(all.hasPermission("Blokker.tempban.melding")){
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.YELLOW + args[0] + ChatColor.RED + " ble tempbannet av " + ChatColor.YELLOW + p1.getDisplayName() + ChatColor.RED + ".");
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.RED + "Tid: " + ChatColor.YELLOW + i + " time(r).");
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.RED + "Grunn: " + ChatColor.YELLOW + me);
						}
					}
				}
			} else if(args[1].contains("d")){
				String s = args[1];
				int i = Integer.parseInt(s.split("d")[0]);
				Calendar d = Calendar.getInstance();

				d.add(Calendar.HOUR, (i * 24));
				Timestamp t = new Timestamp( d.getTimeInMillis());

				main.sql.insert("bandata", new String[] { "player", "type" , "grunn", "until"}, new Object[] { main.getPlayerHandler().getIdFromName(args[0]), 0, me, t.toString()});
				if(tg != null){
					tg.kickPlayer(ChatColor.DARK_RED + "Du ble tempbannet! Grunn: \n" + ChatColor.YELLOW + me + ChatColor.DARK_RED + "\n Tid: \n" + ChatColor.YELLOW + i + " dag(er)!");
					for(Player all : Bukkit.getOnlinePlayers()){
						if(all.hasPermission("Blokker.tempban.melding")){
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.YELLOW + tg.getDisplayName() + ChatColor.RED + " ble tempbannet av " + ChatColor.YELLOW + p1.getDisplayName() + ChatColor.RED + ".");
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.RED + "Tid: " + ChatColor.YELLOW + i + " dag(er).");
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.RED + "Grunn: " + ChatColor.YELLOW + me);
						}
					}
				} else {
					for(Player all : Bukkit.getOnlinePlayers()){
						if(all.hasPermission("Blokker.tempban.melding")){
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.YELLOW + args[0] + ChatColor.RED + " ble tempbannet av " + ChatColor.YELLOW + p1.getDisplayName() + ChatColor.RED + ".");
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.RED + "Tid: " + ChatColor.YELLOW + i + " dag(er).");
							all.sendMessage(ChatColor.DARK_RED + "[Blokker] " + ChatColor.RED + "Grunn: " + ChatColor.YELLOW + me);
						}
					}
				}
			}
		}
		return false;
	}
}
