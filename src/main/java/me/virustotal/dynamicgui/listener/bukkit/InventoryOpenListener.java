package me.virustotal.dynamicgui.listener.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.entity.player.bukkit.BukkitPlayerWrapper;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.bukkit.BukkitInventoryWrapper;

public class InventoryOpenListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void inventoryOpen(InventoryOpenEvent e)
	{
		if(e.getPlayer() instanceof Player)
		{
			Player player = (Player) e.getPlayer();
			PlayerWrapper<?> playerWrapper = new BukkitPlayerWrapper<Player>(player);
			InventoryWrapper<?> inventoryWrapper = new BukkitInventoryWrapper<Inventory>(e.getInventory());
			me.virustotal.dynamicgui.event.inventory.InventoryOpenEvent inventoryOpenEvent = new me.virustotal.dynamicgui.event.inventory.InventoryOpenEvent(playerWrapper, inventoryWrapper);
			DynamicGUI.get().getEventManager().callEvent(inventoryOpenEvent);
		}
	}
}