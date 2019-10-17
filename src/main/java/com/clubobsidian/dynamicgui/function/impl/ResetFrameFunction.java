package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.Slot;

public class ResetFrameFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2386244760460728686L;

	public ResetFrameFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		if(this.getOwner() instanceof Slot)
		{
			Slot slot = (Slot) this.getOwner();
			slot.resetFrame();
			return true;
		}
		
		return false;
	}
}
