package me.tss1410.Blokker.Commands;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sinfo implements CommandExecutor {

	public Main pl;

	public Sinfo(Main plugin) {
		pl = plugin;
	}

	public boolean onCommand(CommandSender Sender, Command command,
			String commandLabel, String[] args) {

		if (Sender instanceof Player) {

			if (command.getName().equalsIgnoreCase("sinfo")) {
				if (Sender.hasPermission("sh.sinfo")) {
					if (args.length == 0) {
						Sender.sendMessage(ChatColor.RED + "/sinfo <spiller>");
					} else if (args.length == 1) {
						Player p = (Player) Sender;
						Spiller s = pl.spillere.get(pl.getPlayerHandler().getUUIDFromName(args[0]));
						Player pn = Bukkit.getServer().getPlayer(args[0]);
						p.sendMessage(ChatColor.GREEN + "Navn: " + pn.getName());
						p.sendMessage(ChatColor.GREEN + "UUID: " + s.getUUID());
						p.sendMessage(ChatColor.GREEN + "ID: " + s.getId());
						
						if (pn.getGameMode().equals(GameMode.CREATIVE)) {
							p.sendMessage(ChatColor.GREEN+ "Spillermodus: Kreativ");
						} else if (pn.getGameMode().equals(GameMode.SURVIVAL)) {
							p.sendMessage(ChatColor.GREEN+ "Spillermodus: Survival");
						}
						p.sendMessage(ChatColor.GREEN + "IP: "+ pn.getAddress().getAddress().toString());
						if (pn.isOp()) {
							p.sendMessage(ChatColor.GREEN + "OP: Ja");
						} else {
							p.sendMessage(ChatColor.GREEN + "OP: Nei");
						}
						p.sendMessage(ChatColor.GREEN + "Sult: "+ pn.getFoodLevel());
						p.sendMessage(ChatColor.GREEN + "XP: "+ pn.getTotalExperience());
						p.sendMessage(ChatColor.GREEN + "Level: "+ pn.getLevel());
						p.sendMessage(ChatColor.GREEN+ "Lokasjon: "+ pn.getPlayer().getLocation().getWorld().getName() + " "+ pn.getPlayer().getLocation().getBlockX()+ " "+ pn.getPlayer().getLocation().getBlockY()+ " "+ pn.getPlayer().getLocation().getBlockZ());
						p.sendMessage(ChatColor.GREEN + "I hånda :"+ pn.getItemInHand().getType() + " Antall: "+ pn.getItemInHand().getAmount());

					}
				} else {
					Sender.sendMessage(ChatColor.RED + "Du har ikke permission");
				}

			}
		} else {
			return false;
		}
		return true;
	}

	private int getGroupID(final String name) {
		try (ResultSet rs = pl.sql
				.query("SELECT `group` FROM players WHERE player='" + name
						+ "'")) {
			if (rs.first())
				return rs.getInt("group");
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return -3;
	}

	private List<String> getPlayersWithGroupID(final int groupID) {
		final List<String> players = new ArrayList<String>();
		try (ResultSet rs = pl.sql
				.query("SELECT player FROM players WHERE `group`='" + groupID
						+ "'")) {
			while (rs.next()) {
				players.add(rs.getString("player"));
			}
			return players;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return players;
	}

	private String getNameFromID(final int i) {
		try (ResultSet rs = pl.sql
				.query("SELECT player FROM players WHERE id='" + i + "'")) {
			if (rs.first()) {
				return rs.getString("player");
			}
			return null;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getGroupNameFromID(final int id) {
		try (ResultSet rs = pl.sql.query("SELECT name FROM groups WHERE id='"
				+ id + "'")) {
			if (rs.first())
				return rs.getString("name");
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
