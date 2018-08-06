package me.virustotal.dynamicgui.listener.bukkit;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.entity.player.bukkit.BukkitPlayerWrapper;
import me.virustotal.dynamicgui.world.LocationWrapper;
import me.virustotal.dynamicgui.world.bukkit.BukkitLocationWrapper;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void interact(final PlayerInteractEvent e)
	{
		 me.virustotal.dynamicgui.event.player.Action action = me.virustotal.dynamicgui.event.player.Action.valueOf(e.getAction().toString());
		 PlayerWrapper<?> playerWrapper = new BukkitPlayerWrapper<Player>(e.getPlayer());
		 LocationWrapper<?> locationWrapper = new BukkitLocationWrapper<Location>(e.getClickedBlock().getLocation());
		 me.virustotal.dynamicgui.event.block.PlayerInteractEvent interactEvent = new me.virustotal.dynamicgui.event.block.PlayerInteractEvent(playerWrapper, locationWrapper, action);
		 DynamicGUI.getInstance().getEventManager().callEvent(interactEvent);
	}
}