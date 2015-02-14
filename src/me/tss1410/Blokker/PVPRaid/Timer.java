package me.tss1410.Blokker.PVPRaid;

import java.util.Date;

import me.tss1410.Blokker.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable 
{
	private final Main pl;


	
	public Timer(Main pl) 
	{
		this.pl = pl;
	}
	Date d = new Date();
	@SuppressWarnings("deprecation")
	@Override
	public void run() {



		pl.ticks++; // Add a tick to the tick counter

		if( pl.ticks % ( 20 ) == 0 ) // Hvert sekund
		{	
			
			if( d.getMinutes() == 50 && d.getSeconds() == 00){
				Bukkit.broadcastMessage(ChatColor.GREEN + "Det er 10 minutter til raid starter!");
			}
			if(d.getMinutes() == 59 && d.getSeconds() == 00){
				Bukkit.broadcastMessage(ChatColor.GREEN + "Det er 1 minutt til raid starter!");
			}
			if(d.getMinutes() == 59 && d.getSeconds() == 30){
				Bukkit.broadcastMessage(ChatColor.GREEN + "Det er 30 sekunder til raid starter!");
			}
			if( d.getMinutes() == 59 && d.getSeconds() == 57){
				Bukkit.broadcastMessage(ChatColor.GREEN + "Det er 3 sekunder til raid starter!");

			}
			if(d.getHours() == 18 && d.getMinutes() == 00) // Om tiden er 18:noe
			{
				if( !pl.isRaiding )
				{

					pl.isRaiding = true;
					Bukkit.broadcastMessage(ChatColor.GREEN + "Raid har started og varer i 1 time!");
					for(Player p : Bukkit.getOnlinePlayers()){
						p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
					}
				}

			} else {
				if(pl.isRaiding == true){
					pl.isRaiding = false;
					Bukkit.broadcastMessage(ChatColor.GREEN + "Raid er skrudd av!");
				}
				
			}
		}

		if( pl.ticks % ( 20L * 1 ) == 0 ) // Every second
		{

		}

		if( pl.ticks % ( 20L * 30 ) == 0 ) // Every 30 second
		{

		}

		if( pl.ticks % ( 20L * 60 ) == 0 ) // Every 60 second
		{

		}

		if( pl.ticks % ( 20L * 60 * 5 ) == 0 ) // Every 5 minutes
		{
			
		}

		if( pl.ticks % ( 20L * 60 * 15 ) == 0 ) // Every 15 minutes
		{
			
		} 
	}
	
}
