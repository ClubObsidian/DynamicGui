package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Slot;

public class CheckMoveableFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1037806025228025407L;

	public CheckMoveableFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		if(this.getData() == null)
		{
			return false;
		}
		
		FunctionOwner owner = this.getOwner();
		if(!(owner instanceof Slot))
		{
			return false;
		}
		
		Slot slot = (Slot) owner;
		Boolean value = Boolean.valueOf(this.getData());
		if(value == null)
		{
			return false;
		}
		
		return value.equals(slot.isMoveable());
	}
}