package com.clubobsidian.dynamicgui.util;

import java.util.List;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.inventory.Click;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.manager.dynamicgui.FunctionManager;

public class FunctionUtil {
	
	public static void tryFunctions(Slot slot, Click InventoryClick, PlayerWrapper<?> player)
	{
		tryFunctions(slot, InventoryClick, player, 0);
	}

	public static void tryFunctions(Slot slot, Click inventoryClick, PlayerWrapper<?> player, int startingIndex)
	{
		FunctionResponse result = null;
		if(slot.getFunctions() != null)
		{
			result = tryFunctions(player, slot.getFunctions(), slot, startingIndex);
			if(!result.result)
			{
				List<Function> failFunctions = slot.getFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(player, failFunctions, slot, startingIndex);
				}
			}
		}
		if(inventoryClick == Click.LEFT && slot.getLeftClickFunctions() != null)
		{
			result = tryFunctions(player, slot.getLeftClickFunctions(), slot, startingIndex);
			if(!result.result)
			{
				List<Function> failFunctions = slot.getLeftClickFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(player, failFunctions, slot, startingIndex);
				}

				return;
			}
		}
		else if(inventoryClick == Click.RIGHT && slot.getRightClickFunctions() != null)
		{
			result = tryFunctions(player, slot.getRightClickFunctions(), slot, startingIndex);
			if(!result.result)
			{
				List<Function> failFunctions = slot.getRightClickFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(player, failFunctions, slot, startingIndex);
				}

				return;
			}
		}
		else if(inventoryClick == Click.MIDDLE && slot.getMiddleClickFunctions() != null)
		{
			result = tryFunctions(player, slot.getMiddleClickFunctions(), slot, startingIndex);
			if(!result.result)
			{
				List<Function> failFunctions = slot.getMiddleClickFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(player, failFunctions, slot, startingIndex);
				}

				return;
			}
		}
	}

	private static FunctionResponse tryFunctions(PlayerWrapper<?> player, List<Function> functions, Slot slot, int startingIndex)
	{
		FunctionResponse response = new FunctionResponse(true);
		for(int i = startingIndex; i < functions.size(); i++)
		{
			Function func = functions.get(i);
			Function myFunc = null;
			try 
			{
				if(FunctionManager.get().getFunctionByName(func.getName()) == null)
				{
					System.out.println("Cannot find " + func.getName() + " continuing!");
					continue;
				}

				myFunc = FunctionManager.get().getFunctionByName(func.getName()).clone();
				myFunc.setData(func.getData());
				myFunc.setOwner(slot);
				myFunc.setIndex(i);
			} 
			catch (IllegalArgumentException | SecurityException ex) 
			{
				ex.printStackTrace();
			}
			boolean result = myFunc.function(player);
			if(!result)
			{
				response = new FunctionResponse(false, func.getName());
				break;
			}
		}
		return response;
	}
	
	private static class FunctionResponse 
	{	
		private boolean result;
		private String failedFunction;
		
		public FunctionResponse(boolean result)
		{
			this(result, null);
		}
		
		public FunctionResponse(boolean result, String failedFunction)
		{
			this.result = result;
			this.failedFunction = failedFunction;
		}
	}
}