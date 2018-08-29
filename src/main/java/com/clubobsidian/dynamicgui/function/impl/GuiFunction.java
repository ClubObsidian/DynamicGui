package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.DynamicGui2;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;

public class GuiFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 848178368629667482L;
	
	public GuiFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(final PlayerWrapper<?> playerWrapper)
	{
		final String gui = this.getData();

		if(playerWrapper.getOpenInventoryWrapper() != null)
		{
			playerWrapper.closeInventory();
		}
		
		if(!GuiManager.get().hasGuiName(gui))
			return false;
		
		DynamicGui2.get().getServer().getScheduler().scheduleSyncDelayedTask(DynamicGui2.get().getPlugin(), new Runnable()
		{
			@Override
			public void run()
			{
				GuiManager.get().openGui(playerWrapper, gui);
			}
		},2L);
		return true;
	}
}
