package me.tss1410.Blokker.Commands;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Saldo implements CommandExecutor{
	
	public Main pl;
	
	public Saldo(Main plugin){
		pl=plugin;
	}
	
	public boolean onCommand(CommandSender Sender, Command command, String commandLabel, String args[]){
		if(!(Sender instanceof Player)){
			Sender.sendMessage(ChatColor.RED + "Kun spillere kan utføre denne kommandoen");
			
		} else {
			Player p = (Player) Sender;
			if(Sender.hasPermission("Blokker.saldo")){
				Spiller s = pl.spillere.get(p.getUniqueId());
				System.out.println(p.getUniqueId());
				System.out.println(s == null);
				
				Sender.sendMessage(ChatColor.GREEN + "Din saldo er " + ChatColor.WHITE + s.getBalance());
				
			}
		}
		
		return false;
		
	}

}
