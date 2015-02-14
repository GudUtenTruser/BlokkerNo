package me.tss1410.Blokker.Diverse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class AltAnnetListener implements Listener{
	
    @EventHandler
    public void onWeather(final WeatherChangeEvent e) {
        e.setCancelled(true);
        Bukkit.broadcastMessage(ChatColor.GRAY + "Været ble satt til sol.");
    }

}
