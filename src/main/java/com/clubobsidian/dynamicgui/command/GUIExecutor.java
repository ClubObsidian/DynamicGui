package com.clubobsidian.dynamicgui.command;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.api.GuiApi;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.gui.GUI;
import com.clubobsidian.dynamicgui.objects.SoundWrapper;

public class GUIExecutor {

	public boolean execute(PlayerWrapper<?> player, String guiName)
	{
		GUI gui = null;

		if(GuiApi.hasGuiName(guiName))
		{
			gui = GuiApi.getGuiByName(guiName);
		}

		if(gui == null)
		{
			player.sendMessage(DynamicGUI.get().getNoGui());
			return false;
		}
		
		boolean permNull = (gui.getPermission() == null);
		if(!permNull)
		{
			if(!player.hasPermission(gui.getPermission()))
			{
				if(gui.getpMessage() == null)
				{
					player.sendMessage(DynamicGUI.get().getNoPermissionGui());
				}
				else
				{
					player.sendMessage(gui.getpMessage());
				}
				return false;
			}
		}
		
		gui.buildInventory(player);

		for(SoundWrapper wrapper : gui.getOpeningSounds())
		{
			wrapper.playSoundToPlayer(player); 
		}
		
		DynamicGUI.get().getServer().getScheduler().scheduleSyncDelayedTask(DynamicGUI.get().getPlugin(), new Runnable()
		{
			@Override
			public void run()
			{
				player.updateInventory();
			}
		},2L);
		
		GuiApi.addGUI(player.getUniqueId(), gui);
		return true;
	}
}