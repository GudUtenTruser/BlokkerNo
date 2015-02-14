package me.tss1410.Blokker.Commands;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Rank implements CommandExecutor{

	public Main pl;

	public Rank(Main plugin){
		pl=plugin;
	}

	public boolean onCommand(CommandSender Sender, Command command, String commandLabel, String args[]){
		if(Sender.hasPermission("Blokker.rank")){
			if(args.length == 2){
				Spiller s = pl.spillere.get(pl.getPlayerHandler().getUUIDFromName(args[0]));
				Player p = (Player) Sender;
				if(s != null && p != null){
					switch(args[1].toLowerCase()){
					case "bruker":
						pl.getChatHandler().setRank(p, me.tss1410.Blokker.Chat.Rank.BRUKER);
						s.setRank(me.tss1410.Blokker.Chat.Rank.BRUKER);
						break;
					case "sponsor":
						pl.getChatHandler().setRank(p, me.tss1410.Blokker.Chat.Rank.SPONSOR);
						s.setRank(me.tss1410.Blokker.Chat.Rank.SPONSOR);
						break;
					case "bygger":
						pl.getChatHandler().setRank(p, me.tss1410.Blokker.Chat.Rank.BYGGER);
						s.setRank(me.tss1410.Blokker.Chat.Rank.BYGGER);
						break;
					case "hjelper":
						pl.getChatHandler().setRank(p, me.tss1410.Blokker.Chat.Rank.HJELPER);
						s.setRank(me.tss1410.Blokker.Chat.Rank.HJELPER);
						break;
					case "mod":
					case "moderator":
						pl.getChatHandler().setRank(p, me.tss1410.Blokker.Chat.Rank.MOD);
						s.setRank(me.tss1410.Blokker.Chat.Rank.MOD);
						break;
					case "ansvarlig":
						pl.getChatHandler().setRank(p, me.tss1410.Blokker.Chat.Rank.ANSVARLIG);
						s.setRank(me.tss1410.Blokker.Chat.Rank.ANSVARLIG);
						break;
					case "eier":
						pl.getChatHandler().setRank(p, me.tss1410.Blokker.Chat.Rank.EIER);
						s.setRank(me.tss1410.Blokker.Chat.Rank.EIER);
						pl.getChatHandler().setPlayerPrefix(p);
						break;
					}
				}
			} else {
				Sender.sendMessage(ChatColor.RED + "Bruk /rank <spiller> <rank>");
			}
		} else {
			Sender.sendMessage(pl.noPerm);
		}
		return false;
	}

}
