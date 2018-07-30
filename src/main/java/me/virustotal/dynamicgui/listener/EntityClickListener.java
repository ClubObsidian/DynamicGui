package me.virustotal.dynamicgui.listener;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.gui.GUI;
import net.citizensnpcs.api.CitizensAPI;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EntityClickListener implements Listener {

	private DynamicGUI plugin;
	public EntityClickListener(DynamicGUI plugin) 
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityClick(PlayerInteractEntityEvent e)
	{
		if(e.getRightClicked() != null)
		{
			Entity entity = e.getRightClicked();
			if(CitizensAPI.getNPCRegistry().isNPC(entity))
			{
				for(GUI gui : GuiApi.getGuis())
				{
					if(gui.getNpcIds().contains(CitizensAPI.getNPCRegistry().getNPC(entity).getId()))
					{
						e.getPlayer().chat("/gui " + gui.getName());
						break;
					}
				}
			}
		}
	}
}