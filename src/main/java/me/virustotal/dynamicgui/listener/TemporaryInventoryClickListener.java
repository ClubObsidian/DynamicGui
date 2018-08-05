package me.virustotal.dynamicgui.listener;

import java.util.List;
import java.util.UUID;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.inventory.InventoryClickEvent;
import me.virustotal.dynamicgui.function.Function;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;
import me.virustotal.dynamicgui.util.FunctionUtil;

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
		for(int j = 0; j < gui.getSlots().size(); j++)
		{
			if(gui.getSlots().get(j) != null)
			{
				try
				{
					String tag = item.getString(DynamicGUI.TAG);
					if(tag != null)
					{
						UUID uuid = UUID.fromString(tag);
						if(gui.getSlots().get(j) != null)
						{
							if(gui.getSlots().get(j).getUUID() != null)
							{
								if(gui.getSlots().get(j).getUUID().equals(uuid))
								{
									slot = gui.getSlots().get(j);
									break;
								}
							}
						}
					}
				}
				catch(SecurityException | IllegalArgumentException ex)
				{
					ex.printStackTrace();
				}
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