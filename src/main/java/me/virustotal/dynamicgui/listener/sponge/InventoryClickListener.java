package me.virustotal.dynamicgui.listener.sponge;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.entity.player.sponge.SpongePlayerWrapper;
import me.virustotal.dynamicgui.event.inventory.Click;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.sponge.SpongeInventoryWrapper;

public class InventoryClickListener {

	@Listener
	public void inventoryClick(ClickInventoryEvent e, @First Player player)
	{
		Click clickType = null;
		if(e instanceof ClickInventoryEvent.Primary)
		{
			clickType = Click.LEFT;
		}
		else if(e instanceof ClickInventoryEvent.Middle)
		{
			clickType = Click.MIDDLE;
		}
		else if (e instanceof ClickInventoryEvent.Secondary)
		{
			clickType = Click.RIGHT;
		}

		DynamicGUI.get().getLogger().info("Event name: " + e.getClass().getName());
		
		DynamicGUI.get().getLogger().info("Click type: " + clickType);
		List<SlotTransaction> transactions = e.getTransactions();
		if(transactions.size() > 0)
		{
			SlotTransaction transaction = transactions.get(0);
			Optional<SlotIndex> slotIndex = transaction.getSlot().getInventoryProperty(SlotIndex.class);
			if(slotIndex.isPresent())
			{
				//Iterator<Inventory> it = e.getTargetInventory().iterator();k
				Inventory inventory = transaction.getSlot().transform().parent();
				
				Optional<InventoryTitle> title = inventory.getInventoryProperty(InventoryTitle.class);
				if(title.isPresent())
				{
					DynamicGUI.get().getLogger().info("inventory: " + title.get().getValue().toPlain());
				}
				
				int slot = slotIndex.get().getValue();
				DynamicGUI.get().getLogger().info("From sponge inventory listener, slot clicked on is: " + slot);
				PlayerWrapper<?> playerWrapper = new SpongePlayerWrapper<Player>(player);
				InventoryWrapper<?> inventoryWrapper = new SpongeInventoryWrapper<Inventory>(inventory);
				me.virustotal.dynamicgui.event.inventory.InventoryClickEvent clickEvent = new me.virustotal.dynamicgui.event.inventory.InventoryClickEvent(playerWrapper, inventoryWrapper, slot, clickType);
				DynamicGUI.get().getEventManager().callEvent(clickEvent);
				if(clickEvent.isCancelled())
				{
					e.setCancelled(true);
				}
				DynamicGUI.get().getLogger().info("Is trident event cancelled: " + clickEvent.isCancelled());
				DynamicGUI.get().getLogger().info("Is sponge event canclled: " + e.isCancelled());
			}
		}
	}
}