package me.tss1410.Blokker.Commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.tss1410.Blokker.Main;
import me.tss1410.Blokker.Players.Spiller;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Bank implements CommandExecutor{

	public Main pl;
	
	public Bank(Main plugin){
		pl=plugin;
	}
	
	public boolean onCommand(CommandSender Sender, Command command, String commandLabel, String args[]){
		

		
		if(!(Sender instanceof Player)){
			Sender.sendMessage(ChatColor.RED + "Kun spillere kan utføre denne kommandoen");
			return false;
		}
		if(args.length == 0){
			if(Sender.hasPermission("Blokker.bank.info")){
				Sender.sendMessage(ChatColor.GREEN + "[Bank Hjelp]");
				Sender.sendMessage(ChatColor.GREEN + "/bank inn " + ChatColor.RESET + " - Veksler mineraler til penger");
				Sender.sendMessage(ChatColor.GREEN + "/saldo " + ChatColor.RESET + " - Hvor mye du har på kontoen");
				Sender.sendMessage(ChatColor.GREEN + "/bank betal [spiller] [antall gull] " + ChatColor.RESET + " - Betaler spiller antall gull");
				Sender.sendMessage(ChatColor.GREEN + "/bank topp " + ChatColor.RESET + " - Viser de 10 spillerene med mest gull");
			} else {
				Sender.sendMessage(pl.noPerm);
			}
		}

		if(args.length == 1){
			if(args[0].equalsIgnoreCase("inn")){
				if(Sender.hasPermission("Blokker.bank.inn")){
					Spiller s = pl.spillere.get(((Player) Sender).getUniqueId());
					Player p = (Player) Sender;
					switch(p.getItemInHand().getType()){

					case EMERALD:
						int g = 0;
						for(int i = 0; i <= p.getItemInHand().getAmount(); i++){
							pl.getBankHandler().addMoney(s, 50);
							g++;
						}
						s.sendMessage(ChatColor.GREEN + "Du solgte " + g + " emeralds for " + (g*50) + " penger");
						p.getInventory().setItemInHand(new ItemStack(Material.AIR));
						break;
					case DIAMOND:
						int h = 0;
						for(int i = 0; i <= p.getItemInHand().getAmount(); i++){
							pl.getBankHandler().addMoney(s, 30);
							h++;
						}
						s.sendMessage(ChatColor.GREEN + "Du solgte " + h + " diamonds for " + (h*30) + " penger");
						p.getInventory().setItemInHand(new ItemStack(Material.AIR));
						break;
					case IRON_INGOT:
						int b = 0;
						for(int i = 0; i <= p.getItemInHand().getAmount(); i++){
							pl.getBankHandler().addMoney(s, 30);
							b++;
						}
						s.sendMessage(ChatColor.GREEN + "Du solgte " + b + " iron ingots for " + (b*5) + " penger");
						p.getInventory().setItemInHand(new ItemStack(Material.AIR));
						break;
					case GOLD_INGOT:
						int n = 0;
						for(int i = 0; i <= p.getItemInHand().getAmount(); i++){
							pl.getBankHandler().addMoney(s, 20);
							n++;
						}
						s.sendMessage(ChatColor.GREEN + "Du solgte " + n + " iron ingots for " + (n*20) + " penger");
						p.getInventory().setItemInHand(new ItemStack(Material.AIR));
						break;
					
					default:
						Sender.sendMessage(ChatColor.RED + "Du kan ikke veksle dette inn i penger");
						break;
					
					}
				} else {
					Sender.sendMessage(pl.noPerm);
				}
			} else if (args[0].equalsIgnoreCase("topp")) {
				if(Sender.hasPermission("Blokker.bank.topp")){
					try
					{
						ResultSet rs = this.pl.sql.query("SELECT balance FROM players ORDER BY balance DESC LIMIT 10");
						int no = 1;
						Sender.sendMessage(ChatColor.DARK_GREEN + "Topp 10 spillere med mest penger:");
						while (rs.next())
						{
							String text = String.format("[%1$d] %2$s - %3$s", new Object[] { Integer.valueOf(no++), rs.getString("name"), Integer.valueOf(rs.getInt("amount")) });

							Sender.sendMessage(ChatColor.GREEN + text);
						}

					}
					catch (SQLException e)
					{
						e.printStackTrace();
					}
				} else {
					Sender.sendMessage(pl.noPerm);
				}

			}
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("betal")) {
				final int betal = Integer.parseInt(args[2]);
				final Player tg = Bukkit.getServer().getPlayer(args[1]);
				if(tg == null){
					Sender.sendMessage(ChatColor.RED + "Fant ikke mottakeren");
					return false;
				}
				try {
					final ResultSet PC = pl.sql.query("SELECT bank FROM players WHERE uuid = '"+ ((Player) Sender).getUniqueId() + "'");
					final ResultSet P = pl.sql.query("SELECT bank FROM players WHERE uuid = '" + tg.getUniqueId() + "'");
					if (PC.next() && P.next()) {
						final int penger = PC.getInt("balance");
						final int tgpenger = P.getInt("balance");
						final int y = tgpenger + betal;
						final int x = penger - betal;
						if ((betal <= penger)) {
							if (tg.getName() != Sender.getName()) {
								pl.sql.query("update `players` set `bank` = '"+ x+ "' where `player`='"+ ((Player) Sender).getUniqueId() + "'");
								pl.sql.query("update `players` set `bank` = '"+ y+ "'  where `player`='"+ tg.getUniqueId() + "'");
								Sender.sendMessage(ChatColor.GREEN+ "Sendt!");
								tg.sendMessage(ChatColor.GREEN+ "Du mottok "+ ChatColor.DARK_GREEN+ betal + ChatColor.GREEN+ " gull av " + Sender.getName());
							} else {
								Sender.sendMessage(ChatColor.RED
										+ "Du kan ikke sende penger til deg selv");
							}
						} else {
							Sender.sendMessage(ChatColor.RED
									+ "Du har ikke nok penger til å sende så mye");
						}
					}
				} catch (final SQLException e) {

					e.printStackTrace();
				}
			}
		}
		
		return false;
		
	}

}
