package com.clubobsidian.dynamicgui.listener.sponge;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.inventory.Inventory;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.sponge.SpongePlayerWrapper;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.sponge.SpongeInventoryWrapper;

public class InventoryCloseListener {

	@Listener
	public void onInventoryClose(InteractInventoryEvent.Close e, @First Player player)
	{
		PlayerWrapper<?> playerWrapper = new SpongePlayerWrapper<Player>(player);
		InventoryWrapper<?> inventoryWrapper = new SpongeInventoryWrapper<Inventory>(e.getTargetInventory());
		DynamicGui.get().getEventManager().callEvent(new com.clubobsidian.dynamicgui.event.inventory.InventoryCloseEvent(playerWrapper, inventoryWrapper));
	}
	
	//TODO - Check if kick event is needed
	@Listener
	public void onQuit(ClientConnectionEvent.Disconnect e, @First Player player)
	{
		PlayerWrapper<Player> playerWrapper = new SpongePlayerWrapper<Player>(player);
		DynamicGui.get().getEventManager().callEvent(new com.clubobsidian.dynamicgui.event.player.PlayerQuitEvent(playerWrapper));
	}
}