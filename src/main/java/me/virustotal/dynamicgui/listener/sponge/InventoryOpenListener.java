package me.virustotal.dynamicgui.listener.sponge;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.Inventory;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.entity.player.sponge.SpongePlayerWrapper;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.sponge.SpongeInventoryWrapper;

public class InventoryOpenListener {

	@Listener
	public void onInventoryOpen(InteractInventoryEvent.Open e, @First Player player)
	{
		PlayerWrapper<?> playerWrapper = new SpongePlayerWrapper<Player>(player);
		InventoryWrapper<?> inventoryWrapper = new SpongeInventoryWrapper<Inventory>(e.getTargetInventory());
		me.virustotal.dynamicgui.event.inventory.InventoryOpenEvent inventoryOpenEvent = new me.virustotal.dynamicgui.event.inventory.InventoryOpenEvent(playerWrapper, inventoryWrapper);
		DynamicGUI.get().getEventManager().callEvent(inventoryOpenEvent);
	}
}