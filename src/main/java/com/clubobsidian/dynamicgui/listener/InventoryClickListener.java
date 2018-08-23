package com.clubobsidian.dynamicgui.listener;

import java.util.List;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.api.GuiApi;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.inventory.InventoryClickEvent;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.GUI;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.util.FunctionUtil;
import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void invClick(final InventoryClickEvent e)
	{
		if(e.getPlayerWrapper().getOpenInventoryWrapper() == null)
		{
			DynamicGUI.get().getLogger().info("Open inventory wrapper is null");
			return;
		}
		if(!GuiApi.hasGuiTitle(e.getPlayerWrapper().getOpenInventoryWrapper().getTitle()))
		{
			DynamicGUI.get().getLogger().info("Open gui does not have correct title, title is : " + e.getPlayerWrapper().getOpenInventoryWrapper().getTitle());
			return;
		}
		
		e.setCancelled(true);
		
		DynamicGUI.get().getLogger().info("From trident index is: " + e.getSlot());
		DynamicGUI.get().getLogger().info("From trident title is: " + e.getInventoryWrapper().getTitle());
		
		ItemStackWrapper<?> item = e.getInventoryWrapper().getItem(e.getSlot());
		if(item.getItemStack() == null)
		{
			DynamicGUI.get().getLogger().info("ItemStack is null");
			return;
		}
		else
		{
			DynamicGUI.get().getLogger().info("From trident, itemstack is: " + item.getItemStack());
		}
		
		if(e.getClick() == null) //For other types of clicks besides left, right, middle
			return;

		DynamicGUI.get().getLogger().info("Click is not null");
		PlayerWrapper<?> player = e.getPlayerWrapper();
		if(GuiApi.hasGUICurrently(player))
		{
			DynamicGUI.get().getLogger().info("Has gui currently");
			if(GuiApi.hasGuiTitle(e.getInventoryWrapper().getTitle()))
			{
				DynamicGUI.get().getLogger().info("GuiApi has title");
				GUI gui = GuiApi.getCurrentGUI(player);
				Slot slot = null;
				for(Slot s : gui.getSlots())
				{
					if(e.getSlot() == s.getIndex())
					{
						slot = s;
						break;
					}
				}

				if(slot == null)
					return;

				List<Function> functions = slot.getFunctions();
				List<Function> leftClickFunctions = slot.getLeftClickFunctions();
				List<Function> rightClickFunctions = slot.getRightClickFunctions();
				List<Function> middleClickFunctions = slot.getMiddleClickFunctions();

				if(functions == null && leftClickFunctions == null && rightClickFunctions == null && middleClickFunctions != null)
					return;

				Boolean close = null;
				if(slot.getClose() != null)
					close = slot.getClose();
				else if(gui.getClose() != null)
					close = gui.getClose();
				else
					close = true;

				if(close)
					player.closeInventory();

				FunctionUtil.tryFunctions(slot, e.getClick(), player);

			}
		}
	}
}