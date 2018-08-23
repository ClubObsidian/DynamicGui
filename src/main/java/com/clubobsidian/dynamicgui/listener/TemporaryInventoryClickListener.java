package com.clubobsidian.dynamicgui.listener;

import java.util.List;

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

public class TemporaryInventoryClickListener implements Listener {

	@EventHandler
	public void invClick(final InventoryClickEvent e)
	{
		if(e.getInventoryWrapper() == null || e.getInventoryWrapper().getInventory() == null)
			return;
		if(e.getPlayerWrapper().getOpenInventoryWrapper() == null)
			return;
		if(e.getPlayerWrapper().getOpenInventoryWrapper().getInventory() == null)
			return;
		if(GuiApi.getTemporaryGuiForPlayer(e.getPlayerWrapper()) == null)
			return;

		e.setCancelled(true);
		
		ItemStackWrapper<?> item = e.getInventoryWrapper().getItem(e.getSlot());
		if(item == null || item.getItemStack() == null)
			return;

		PlayerWrapper<?> player = e.getPlayerWrapper();

		GUI gui = GuiApi.getTemporaryGuiForPlayer(e.getPlayerWrapper());

		if(gui == null)
			return;

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