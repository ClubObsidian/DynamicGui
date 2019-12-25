package com.clubobsidian.dynamicgui.function.impl.gui;

import java.util.ArrayList;
import java.util.List;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;

public class RefreshSlotFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1079816229207205846L;

	public RefreshSlotFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		if(this.getData() == null)
		{
			if(this.getOwner() instanceof Gui)
			{
				return false;
			}
			
			Slot slot = (Slot) this.getOwner();
			slot.setUpdate(true);
			return true;
		}
		
		try
		{
			String data = this.getData();
			List<Integer> slotIndexs = new ArrayList<>();
			if(!data.contains(","))
			{
				int parsed = Integer.parseInt(data);
				slotIndexs.add(parsed);
			}
			else
			{
				String[] split = data.split(",");
				for(String str : split)
				{
					Integer parsed = Integer.parseInt(str);
					slotIndexs.add(parsed);
				}
			}
			
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
			
			List<Slot> slots = gui.getSlots();
			for(Slot slot : slots)
			{
				if(slotIndexs.contains(slot.getIndex()))
				{
					slot.setUpdate(true);
				}
			}
			
			return true;
		}
		catch(NumberFormatException ex)
		{
			return false;
		}
	}
}