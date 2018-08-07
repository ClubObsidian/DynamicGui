package me.virustotal.dynamicgui.listener.sponge;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.inventory.Inventory;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.entity.player.sponge.SpongePlayerWrapper;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.sponge.SpongeInventoryWrapper;

public class InventoryCloseListener {

	@Listener
	public void onInventoryClose(InteractInventoryEvent.Close e, @First Player player)
	{
		PlayerWrapper<?> playerWrapper = new SpongePlayerWrapper<Player>(player);
		InventoryWrapper<?> inventoryWrapper = new SpongeInventoryWrapper<Inventory>(e.getTargetInventory());
		DynamicGUI.get().getEventManager().callEvent(new me.virustotal.dynamicgui.event.inventory.InventoryCloseEvent(playerWrapper, inventoryWrapper));
	}
	
	//TODO - Check if kick event is needed
	@Listener
	public void onQuit(ClientConnectionEvent.Disconnect e, @First Player player)
	{
		PlayerWrapper<Player> playerWrapper = new SpongePlayerWrapper<Player>(player);
		DynamicGUI.get().getEventManager().callEvent(new me.virustotal.dynamicgui.event.player.PlayerQuitEvent(playerWrapper));
	}
}