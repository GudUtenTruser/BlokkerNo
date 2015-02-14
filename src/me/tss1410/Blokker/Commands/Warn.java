package me.tss1410.Blokker.Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Warn implements CommandExecutor {

	public Main pl;

	public Warn(final Main plugin) {
		pl = plugin;
	}



	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String commandLabel, final String args[]) {
		if (Sender instanceof Player) {
			if (command.getName().equalsIgnoreCase("warn")) {
				Player p = (Player) Sender;
				if (p.hasPermission("Blokker.warn")) {
					if (args.length > 1) {
						final String tg = args[0];
						final StringBuffer me = new StringBuffer();
						for (int i = 1; i < args.length; i++) {
							me.append(args[i] + " ");
						}
						Spiller s = pl.spillere.get(((Player) Sender).getUniqueId());
						Spiller target = null;
						
						for(Spiller spillere : pl.spillere.values()){
							if(spillere.getName().equalsIgnoreCase(args[0])){
								target = s;
							}
						}
						
						if(target == null){
							Sender.sendMessage(ChatColor.RED + "Kunne ikke finne spilleren");
							return false;
						}
						//////////////////////

						DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Calendar cal = Calendar.getInstance();

						pl.sql.insert("warndata",new String[] { "reason", "player", "setter","setdate" },new Object[] { me.toString(), target.getId(),s.getId(), formatter.format(cal.getTime()) });
						p.sendMessage(ChatColor.DARK_RED + tg + ChatColor.RED+ " fikk en advarsel");
						p.sendMessage(ChatColor.DARK_RED + "Grunn: "+ ChatColor.RED + me);
						if (Bukkit.getPlayer(tg) != null) {
							Bukkit.getPlayer(tg).sendMessage(ChatColor.RED + "Du fikk en advarsel. Skriv /lw for å sjekke dine advarsler");
						}
						Bukkit.broadcast(ChatColor.GOLD+ "[StabAlarm] MIDDELS: "+ ChatColor.WHITE + p.getName()+ " ga en advarsel til " + tg,"Blokker.ss");
						Bukkit.broadcast(ChatColor.GOLD+ "[StabAlarm] MIDDELS: " + ChatColor.GREEN+ "Grunn: " + me, "Blokker.ss");
						Bukkit.broadcast(ChatColor.GOLD+ "[StabAlarm] MIDDELS: " + ChatColor.GREEN+ tg + " har nå " + countWarns(target.getId())+ " advarsler", "Blokker.ss");
						/////////////////////////////



					} else {
						p.sendMessage(ChatColor.RED + "/warn <spiller> <grunn>");
					}
				}

			}
		}
		return false;
	}

	public int countWarns(int i) {
		int warnings = 0;
		try {
			final ResultSet PC = pl.sql.query("SELECT COUNT(*) FROM warndata WHERE player = '"+ i + "'");
			PC.first();
			warnings = PC.getInt(1);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return warnings;
	}
	
}
