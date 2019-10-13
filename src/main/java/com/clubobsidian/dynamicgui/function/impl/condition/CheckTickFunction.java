package com.clubobsidian.dynamicgui.function.impl.condition;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.udojava.evalex.Expression;

public class CheckTickFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9209750645416892269L;

	public CheckTickFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		FunctionOwner owner = this.getOwner();
		if(owner instanceof Slot)
		{
			Slot slot = (Slot) owner;
			int tick = slot.getCurrentTick();
			int frame = slot.getFrame();
			
			try
			{
				String tickData = this.getData()
						.replace("%tick%", String.valueOf(tick))
						.replace("%frame%", String.valueOf(frame));
				Expression expr = new Expression(tickData);
				expr.addLazyFunction(new EqualLazyFunction());
				
				if(!expr.isBoolean())
				{
					return false;
				}
				
				return expr.eval().intValue() == 1;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
		}
		return false;
	}

}
