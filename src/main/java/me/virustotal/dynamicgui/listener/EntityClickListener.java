package me.virustotal.dynamicgui.listener;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.entity.EntityWrapper;
import me.virustotal.dynamicgui.event.inventory.PlayerInteractEntityEvent;
import me.virustotal.dynamicgui.gui.GUI;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

public class EntityClickListener implements Listener {

	@EventHandler
	public void onEntityClick(PlayerInteractEntityEvent e)
	{
		EntityWrapper<?> entityWrapper = e.getEntityWrapper();
		if(DynamicGUI.get().getPlugin().isNPC(entityWrapper))
		{
			for(GUI gui : GuiApi.getGuis())
			{
				if(gui.getNpcIds().contains(DynamicGUI.get().getPlugin().getNPC(entityWrapper).getId()))
				{
					e.getPlayerWrapper().chat("/gui " + gui.getName());
					break;
				}
			}
		}
	}
}