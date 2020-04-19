package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.Slot;

public class ResetTickFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6376946053337191934L;

	public ResetTickFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		if(this.getOwner() instanceof Slot)
		{
			Slot slot = (Slot) this.getOwner();
			slot.resetTick();
			return true;
		}
		
		return false;
	}
}
