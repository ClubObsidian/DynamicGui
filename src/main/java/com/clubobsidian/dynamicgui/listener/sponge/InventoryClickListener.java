package com.clubobsidian.dynamicgui.listener.sponge;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.sponge.SpongePlayerWrapper;
import com.clubobsidian.dynamicgui.event.inventory.Click;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.sponge.SpongeInventoryWrapper;

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
				Optional<ItemStack> itemStack = transaction.getSlot().peek();
				if(itemStack.isPresent())
				{
					DynamicGUI.get().getLogger().info("ItemStack is there for slot: " + slot);
				}
				else
				{
					DynamicGUI.get().getLogger().info("ItemStack does not exist for slot: " + slot);
				}
				
				PlayerWrapper<?> playerWrapper = new SpongePlayerWrapper<Player>(player);
				InventoryWrapper<?> inventoryWrapper = new SpongeInventoryWrapper<Inventory>(inventory);
				com.clubobsidian.dynamicgui.event.inventory.InventoryClickEvent clickEvent = new com.clubobsidian.dynamicgui.event.inventory.InventoryClickEvent(playerWrapper, inventoryWrapper, slot, clickType);
				DynamicGUI.get().getEventManager().callEvent(clickEvent);
				if(clickEvent.isCanceled())
				{
					e.setCancelled(true);
				}
				DynamicGUI.get().getLogger().info("Is trident event cancelled: " + clickEvent.isCanceled());
				DynamicGUI.get().getLogger().info("Is sponge event canclled: " + e.isCancelled());
			}
		}
	}
}