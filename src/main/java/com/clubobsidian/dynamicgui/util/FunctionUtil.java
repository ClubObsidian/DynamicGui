package com.clubobsidian.dynamicgui.util;

import java.util.List;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.inventory.Click;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.manager.dynamicgui.FunctionManager;

public class FunctionUtil {
	
	public static boolean tryFunctions(FunctionOwner owner, PlayerWrapper<?> playerWrapper)
	{
		FunctionResponse result = null;
		if(owner.getFunctions() != null)
		{
			result = tryFunctions(playerWrapper, owner.getFunctions(), owner);
			if(!result.result)
			{
				List<Function> failFunctions = owner.getFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(playerWrapper, failFunctions, owner);
				}
				return false;
			}
		}
		return true;
	}
	
	public static void tryFunctions(Slot slot, Click InventoryClick, PlayerWrapper<?> playerWrapper)
	{
		tryFunctions(slot, InventoryClick, playerWrapper, 0);
	}

	private static void tryFunctions(Slot slot, Click inventoryClick, PlayerWrapper<?> playerWrapper, int startingIndex)
	{
		FunctionResponse result = null;
		if(slot.getFunctions() != null)
		{
			result = tryFunctions(playerWrapper, slot.getFunctions(), slot);
			if(!result.result)
			{
				List<Function> failFunctions = slot.getFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(playerWrapper, failFunctions, slot);
				}
			}
		}
		if(inventoryClick == Click.LEFT && slot.getLeftClickFunctions() != null)
		{
			result = tryFunctions(playerWrapper, slot.getLeftClickFunctions(), slot);
			if(!result.result)
			{
				List<Function> failFunctions = slot.getLeftClickFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(playerWrapper, failFunctions, slot);
				}

				return;
			}
		}
		else if(inventoryClick == Click.RIGHT && slot.getRightClickFunctions() != null)
		{
			result = tryFunctions(playerWrapper, slot.getRightClickFunctions(), slot);
			if(!result.result)
			{
				List<Function> failFunctions = slot.getRightClickFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(playerWrapper, failFunctions, slot);
				}

				return;
			}
		}
		else if(inventoryClick == Click.MIDDLE && slot.getMiddleClickFunctions() != null)
		{
			result = tryFunctions(playerWrapper, slot.getMiddleClickFunctions(), slot);
			if(!result.result)
			{
				List<Function> failFunctions = slot.getMiddleClickFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(playerWrapper, failFunctions, slot);
				}

				return;
			}
		}
	}

	private static FunctionResponse tryFunctions(PlayerWrapper<?> playerWrapper, List<Function> functions, FunctionOwner owner)
	{
		FunctionResponse response = new FunctionResponse(true);
		for(int i = 0; i < functions.size(); i++)
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
				myFunc.setOwner(owner);
				myFunc.setIndex(i);
			} 
			catch (IllegalArgumentException | SecurityException ex) 
			{
				ex.printStackTrace();
			}
			boolean result = myFunc.function(playerWrapper);
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