package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;

public class SetCloseFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5671625221707551692L;

	public SetCloseFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		try
		{
			Boolean value = Boolean.valueOf(this.getData());
			if(value != null)
			{
				if(this.getOwner() instanceof Slot)
				{
					Slot slot = (Slot) this.getOwner();
					slot.setClose(value);
					return true;
				}
				else if(this.getOwner() instanceof Gui)
				{
					Gui gui = (Gui) this.getOwner();
					gui.setClose(value);
					return true;
				}
			}
		}
		catch(Exception ex)
		{
			DynamicGui.get().getLogger().info("Error parsing value " + this.getData() + " for setclose function");
			ex.printStackTrace();
		}
		return false;
	}
}
