package me.virustotal.dynamicgui.command;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.objects.SoundWrapper;

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