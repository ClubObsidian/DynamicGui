package me.virustotal.dynamicgui.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.inventory.InventoryClickEvent;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;
import me.virustotal.dynamicgui.nbt.NBTItem;
import me.virustotal.dynamicgui.objects.Function;
import me.virustotal.dynamicgui.plugin.DynamicGUIPlugin;
import me.virustotal.dynamicgui.util.FunctionUtil;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

public class InventoryClickListener<T,U> implements Listener {

	private DynamicGUI<T,U> plugin;
	public InventoryClickListener(DynamicGUI<T,U> plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void invClick(final InventoryClickEvent<T,U> e)
	{
		if(e.getPlayerWrapper().getOpenInventoryWrapper() == null)
			return;
		if(!GuiApi.hasGuiTitle(e.getPlayerWrapper().getOpenInventoryWrapper().getTitle()))
			return;
		
		ItemStackWrapper<?> item = e.getInventoryWrapper().getItem(e.getSlot());
		if(item.getItemStack() == null)
			return;

		PlayerWrapper<T> player = e.getPlayerWrapper();
		if(GuiApi.hasGUICurrently(player))
		{
			if(GuiApi.hasGuiTitle(e.getInventoryWrapper().getTitle()))
			{
				GUI gui = GuiApi.getCurrentGUI(player);
				Slot slot = null;
				for(int j = 0; j < gui.getSlots().size(); j++)
				{
					if(gui.getSlots().get(j) != null)
					{
						try
						{
							NBTItem nbtItem = new NBTItem(item);
							if(nbtItem.hasKey(DynamicGUI.TAG))
							{
								UUID uuid = UUID.fromString(nbtItem.getString(DynamicGUI.TAG));
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
	}
}