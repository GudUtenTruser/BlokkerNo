package me.tss1410.Blokker.Commands;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.WorkPlayerData;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Work implements CommandExecutor{
	
	public Main pl;
	
	public Work(Main plugin){
		pl=plugin;
	}
	
	public boolean onCommand(CommandSender Sender, Command command, String commandLabel, String args[]){
		
		if(Sender.hasPermission("Blokker.work") == false){
			Sender.sendMessage(ChatColor.RED + "Du har ikke tilgang til å utføre denne kommandoen");
			return false;
		}
		
		if(!(Sender instanceof Player)){
			return false;
		}

		Player p = (Player) Sender;

		if(!pl.work.containsKey(p.getUniqueId())){
			pl.work.put(p.getUniqueId(), new WorkPlayerData(p.getUniqueId(),p.getInventory().getContents(), p.getInventory().getArmorContents()) );
			p.getInventory().clear();
			p.setGameMode(GameMode.CREATIVE);
			p.getInventory().addItem(new ItemStack(Material.STICK));
			p.getInventory().addItem(new ItemStack(Material.WATCH));
			p.getInventory().addItem(new ItemStack(Material.SLIME_BALL));
			p.sendMessage(ChatColor.GREEN + "Du skrudde på work modus");

			return false;
		} else {
			p.getInventory().clear();
			p.setGameMode(GameMode.SURVIVAL);
			p.getInventory().setContents(pl.work.get(p.getUniqueId()).getInventory());
			p.getInventory().setArmorContents(pl.work.get(p.getUniqueId()).getArmor());
			pl.work.remove(p.getUniqueId());
			p.sendMessage(ChatColor.RED + "Du skrudde av work modus");
			return false;
		}

	}

}
