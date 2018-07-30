package me.virustotal.dynamicgui.listener.bukkit;

import com.clubobsidian.trident.Listener;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.EntityWrapper;
import me.virustotal.dynamicgui.entity.impl.BukkitEntityWrapper;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.entity.player.impl.BukkitPlayerWrapper;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EntityClickListener implements Listener {

	@EventHandler
	public void onEntityClick(PlayerInteractEntityEvent e)
	{
		if(e.getRightClicked() != null)
		{
			PlayerWrapper<Player> playerWrapper = new BukkitPlayerWrapper<Player>(e.getPlayer());
			EntityWrapper<Entity> entityWrapper = new BukkitEntityWrapper<Entity>(e.getRightClicked());
			
			DynamicGUI.getInstance().getEventManager().callEvent(new me.virustotal.dynamicgui.event.inventory.PlayerInteractEntityEvent<Player,Entity>(playerWrapper, entityWrapper));
		}
	}
}