package me.virustotal.dynamicgui.listener;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.GuiApi;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryOpenListener implements Listener {

	private DynamicGUI plugin;
	public InventoryOpenListener(DynamicGUI plugin)
	{
		this.plugin = plugin;
	}


	@EventHandler
	public void inventoryOpen(final InventoryOpenEvent e)
	{
		if(this.plugin.invPlayers.contains(e.getPlayer().getName()))
		{
			if(e.getPlayer().getOpenInventory() != null)
				e.getPlayer().closeInventory();
			return;
		}
		
		if(GuiApi.hasGuiTitle(e.getInventory().getTitle()))
		{
				this.plugin.invPlayers.add(e.getPlayer().getName());
				Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
				{
					@Override
					public void run()
					{
						plugin.invPlayers.remove(e.getPlayer().getName());
					}
				},3L);
			}
	}
}
