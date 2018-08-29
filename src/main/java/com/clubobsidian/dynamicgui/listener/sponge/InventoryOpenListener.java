package com.clubobsidian.dynamicgui.listener.sponge;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.Inventory;

import com.clubobsidian.dynamicgui.DynamicGui2;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.sponge.SpongePlayerWrapper;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.sponge.SpongeInventoryWrapper;

public class InventoryOpenListener {

	@Listener
	public void onInventoryOpen(InteractInventoryEvent.Open e, @First Player player)
	{
		PlayerWrapper<?> playerWrapper = new SpongePlayerWrapper<Player>(player);
		InventoryWrapper<?> inventoryWrapper = new SpongeInventoryWrapper<Inventory>(e.getTargetInventory());
		com.clubobsidian.dynamicgui.event.inventory.InventoryOpenEvent inventoryOpenEvent = new com.clubobsidian.dynamicgui.event.inventory.InventoryOpenEvent(playerWrapper, inventoryWrapper);
		DynamicGui2.get().getEventManager().callEvent(inventoryOpenEvent);
	}
}