package com.clubobsidian.dynamicgui.manager.dynamicgui;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.util.FunctionUtil;

public class AnimationManager {

	private static AnimationManager instance;
	
	public static AnimationManager get()
	{
		if(instance == null)
		{
			instance = new AnimationManager();
		}
		return instance;
	}
	
	
	private AnimationManager()
	{
		this.createScheduler();
	}
	
	private void createScheduler()
	{
		DynamicGui.get().getServer().getScheduler().scheduleSyncRepeatingTask(DynamicGui.get().getPlugin(), new Runnable()
		{
			@Override
			public void run()
			{
				Iterator<Entry<UUID, Gui>> it = GuiManager.get().getPlayerGuis().entrySet().iterator();
				while(it.hasNext())
				{
					Entry<UUID, Gui> next = it.next();
					UUID key = next.getKey();
					PlayerWrapper<?> playerWrapper = DynamicGui.get().getServer().getPlayer(key);
					Gui gui = next.getValue();
					
					boolean updated = false;
					for(Slot slot : gui.getSlots())
					{
						if(slot.getUpdateInterval() == 0)
						{
							continue;
						}
						
						slot.tick();
						if(slot.getCurrentTick() % slot.getUpdateInterval() == 0)
						{
							updated = true;
							ItemStackWrapper<?> itemStackWrapper = slot.buildItemStack(playerWrapper);
							int slotIndex = slot.getIndex();
						
							InventoryWrapper<?> inventoryWrapper = slot.getOwner().getInventoryWrapper();
							inventoryWrapper.setItem(slotIndex, itemStackWrapper);
							
							FunctionUtil.tryFunctions(slot, FunctionType.LOAD, playerWrapper);
						}
					}
					
					if(updated)
					{
						playerWrapper.updateInventory();
					}
				}
			}
		}, 1L, 1L);
	}
}