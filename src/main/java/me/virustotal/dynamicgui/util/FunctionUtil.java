package me.virustotal.dynamicgui.util;

import java.util.List;

import org.bukkit.event.inventory.ClickType;

import me.virustotal.dynamicgui.api.FunctionApi;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.objects.Function;

public class FunctionUtil {
	
	public static void tryFunctions(Slot slot, ClickType clickType, PlayerWrapper<?> player)
	{
		tryFunctions(slot, clickType, player, 0);
	}

	public static void tryFunctions(Slot slot, ClickType clickType, PlayerWrapper<?> player, int startingIndex)
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
		if(clickType == ClickType.LEFT && slot.getLeftClickFunctions() != null)
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
		else if(clickType == ClickType.RIGHT && slot.getRightClickFunctions() != null)
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
		else if(clickType == ClickType.MIDDLE && slot.getMiddleClickFunctions() != null)
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
				if(FunctionApi.getFunctionByName(func.getName()) == null)
				{
					System.out.println("Cannot find " + func.getName() + " continuing!");
					continue;
				}

				myFunc = FunctionApi.getFunctionByName(func.getName()).clone();
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