package me.virustotal.dynamicgui.listener.bukkit;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.entity.player.bukkit.BukkitPlayerWrapper;
import me.virustotal.dynamicgui.world.BlockWrapper;
import me.virustotal.dynamicgui.world.bukkit.BukkitBlockWrapper;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void interact(final PlayerInteractEvent e)
	{
		 me.virustotal.dynamicgui.event.player.Action action = me.virustotal.dynamicgui.event.player.Action.valueOf(e.getAction().toString());
		 PlayerWrapper<?> playerWrapper = new BukkitPlayerWrapper<Player>(e.getPlayer());
		 BlockWrapper<?> blockWrapper = new BukkitBlockWrapper<Block>(e.getClickedBlock());
		 me.virustotal.dynamicgui.event.block.PlayerInteractEvent interactEvent = new me.virustotal.dynamicgui.event.block.PlayerInteractEvent(playerWrapper, blockWrapper, action);
		 DynamicGUI.getInstance().getEventManager().callEvent(interactEvent);
	}
}