package com.clubobsidian.dynamicgui.listener.bukkit;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.clubobsidian.dynamicgui.DynamicGui2;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.bukkit.BukkitPlayerWrapper;
import com.clubobsidian.dynamicgui.world.LocationWrapper;
import com.clubobsidian.dynamicgui.world.bukkit.BukkitLocationWrapper;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void interact(final PlayerInteractEvent e)
	{
		if(e.getClickedBlock() != null)
		{
			com.clubobsidian.dynamicgui.event.player.Action action = com.clubobsidian.dynamicgui.event.player.Action.valueOf(e.getAction().toString());
		 	PlayerWrapper<?> playerWrapper = new BukkitPlayerWrapper<Player>(e.getPlayer());
		 	LocationWrapper<?> locationWrapper = new BukkitLocationWrapper<Location>(e.getClickedBlock().getLocation());
		 	com.clubobsidian.dynamicgui.event.block.PlayerInteractEvent interactEvent = new com.clubobsidian.dynamicgui.event.block.PlayerInteractEvent(playerWrapper, locationWrapper, action);
		 	DynamicGui2.get().getEventManager().callEvent(interactEvent);
		}
	}
}