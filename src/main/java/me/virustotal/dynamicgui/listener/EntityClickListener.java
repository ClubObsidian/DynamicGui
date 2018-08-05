package me.virustotal.dynamicgui.listener;

import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.entity.EntityWrapper;
import me.virustotal.dynamicgui.event.inventory.PlayerInteractEntityEvent;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.plugin.DynamicGUIPlugin;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

public class EntityClickListener implements Listener {

	private DynamicGUIPlugin<?,?> plugin;
	public EntityClickListener(DynamicGUIPlugin<?,?> plugin) 
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityClick(PlayerInteractEntityEvent e)
	{
		EntityWrapper<?> entityWrapper = e.getEntityWrapper();
		if(this.plugin.isNPC(entityWrapper))
		{
			for(GUI gui : GuiApi.getGuis())
			{
				if(gui.getNpcIds().contains(this.plugin.getNPC(entityWrapper).getId()))
				{
					e.getPlayerWrapper().chat("/gui " + gui.getName());
					break;
				}
			}
		}
	}
}