package com.clubobsidian.dynamicgui.function.impl.gui;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;

public class RefreshGuiFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8608158103976585358L;

	public RefreshGuiFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		Gui gui = null;
		FunctionOwner owner = this.getOwner();
		if(owner instanceof Slot)
		{
			Slot slot = (Slot) owner;
			gui = slot.getOwner();
		}
		else if(owner instanceof Gui)
		{
			gui = (Gui) owner;
		}
		
		for(Slot slot : gui.getSlots())
		{
			slot.setUpdate(true);
		}
		
		return true;
	}
}