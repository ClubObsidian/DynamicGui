package me.virustotal.dynamicgui.listener;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.event.inventory.InventoryOpenEvent;

public class InventoryOpenListener<T,U> implements Listener {

	private DynamicGUI<T,U> plugin;
	public InventoryOpenListener(DynamicGUI<T,U> plugin)
	{
		this.plugin = plugin;
	}


	@EventHandler
	public void inventoryOpen(final InventoryOpenEvent<T,U> e)
	{
		if(this.plugin.invPlayers.contains(e.getPlayerWrapper().getName()))
		{
			if(e.getPlayerWrapper().getOpenInventoryWrapper().getInventory() != null)
				e.getPlayerWrapper().closeInventory();
			return;
		}
		
		if(GuiApi.hasGuiTitle(e.getInventoryWrapper().getTitle()))
		{
				this.plugin.invPlayers.add(e.getPlayerWrapper().getName());
				Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
				{
					@Override
					public void run()
					{
						plugin.invPlayers.remove(e.getPlayerWrapper().getName());
					}
				},3L);
			}
	}
}
