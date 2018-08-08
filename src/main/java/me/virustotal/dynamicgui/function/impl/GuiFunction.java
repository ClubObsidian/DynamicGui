package me.virustotal.dynamicgui.function.impl;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.command.GUIExecutor;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;

public class GuiFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 848178368629667482L;

	public GuiFunction(String name, String data) 
	{
		super(name,data);
	}
	
	public GuiFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(final PlayerWrapper<?> player)
	{
		final String gui = this.getData();

		if(player.getOpenInventoryWrapper() != null)
		{
			player.closeInventory();
		}
		
		if(!GuiApi.hasGuiName(gui))
			return false;
		
		DynamicGUI.get().getServer().getScheduler().scheduleSyncDelayedTask(DynamicGUI.get().getPlugin(), new Runnable()
		{
			@Override
			public void run()
			{
				new GUIExecutor().execute(player, gui);
			}
		},2L);
		return true;
	}
}
