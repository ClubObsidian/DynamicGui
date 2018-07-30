package me.virustotal.dynamicgui.listener;

import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.event.inventory.PlayerInteractEntityEvent;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.plugin.DynamicGUIPlugin;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

public class EntityClickListener<T,U> implements Listener {

	private DynamicGUIPlugin<T,U> plugin;
	public EntityClickListener(DynamicGUIPlugin<T,U> plugin) 
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityClick(PlayerInteractEntityEvent<T,U> e)
	{
		U entity = e.getEntityWrapper().getEntity();
		if(this.plugin.isNPC(entity))
		{
			for(GUI gui : GuiApi.getGuis())
			{
				if(gui.getNpcIds().contains(this.plugin.getNPC(entity).getId()))
				{
					e.getPlayerWrapper().chat("/gui " + gui.getName());
					break;
				}
			}
		}
	}
}