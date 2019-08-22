package com.clubobsidian.dynamicgui.manager.dynamicgui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

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
				List<TitleUpdater> titleUpdates = new ArrayList<>();
				Iterator<Entry<UUID, Gui>> it = GuiManager.get().getPlayerGuis().entrySet().iterator();
				while(it.hasNext())
				{
					Entry<UUID, Gui> next = it.next();
					UUID key = next.getKey();
					PlayerWrapper<?> playerWrapper = DynamicGui.get().getServer().getPlayer(key);
					Gui gui = next.getValue();
					
					boolean updated = false;
					
					if(gui.getUpdateInterval() != 0)
					{
						gui.tick();

						if(gui.getCurrentTick() % gui.getUpdateInterval() == 0)
						{
							String title = gui.getTitle();
							title = ReplacerManager.get().replace(title, playerWrapper);
							title = AnimationReplacerManager.get().replace(gui, playerWrapper, title);
							titleUpdates.add(new TitleUpdater(gui, playerWrapper, title));
							updated = true;
						}
					}


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
						}
					}
					
					if(updated)
					{
						playerWrapper.updateInventory();
					}
					
					//Schedule title updates for the next tick
					DynamicGui.get().getServer().getScheduler().scheduleSyncDelayedTask(DynamicGui.get().getPlugin(), () -> 
					{
						for(TitleUpdater updater : titleUpdates)
						{
							updater.update();
						}
					}, 1L);
				}
			}
		}, 1L, 1L);
	}
	
	private static class TitleUpdater
	{
		private Gui gui;
		private PlayerWrapper<?> playerWrapper;
		private String title;
		TitleUpdater(Gui gui, PlayerWrapper<?> playerWrapper, String title)
		{
			this.gui = gui;
			this.playerWrapper = playerWrapper;
			this.title = title;
		}
		
		public boolean update()
		{
			Gui currentGui = GuiManager.get().getCurrentGui(this.playerWrapper);
			if(currentGui != null && currentGui.equals(this.gui))
			{
				this.playerWrapper.updateInventory();
				this.gui.getInventoryWrapper().setTitle(this.playerWrapper, this.title);
				this.playerWrapper.updateInventory();
			}
			else
			{
				this.playerWrapper.closeInventory();
			}
			
			return false;
		}
	}
}