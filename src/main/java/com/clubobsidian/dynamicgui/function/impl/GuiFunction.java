package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.api.GuiApi;
import com.clubobsidian.dynamicgui.command.GUIExecutor;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;

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
