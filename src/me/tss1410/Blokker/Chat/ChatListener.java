package me.tss1410.Blokker.Chat;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener{
	
	@EventHandler
	public void onPlayerChat(final AsyncPlayerChatEvent e){
		String m = e.getMessage();
		e.setCancelled(true);
		Bukkit.broadcastMessage(e.getPlayer().getDisplayName() + ": " + m);
	}

}
