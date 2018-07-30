package me.virustotal.dynamicgui.utils;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import me.virustotal.dynamicgui.api.FunctionApi;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.objects.Function;

public class FunctionUtil {
	
	public static void tryFunctions(Slot slot, ClickType clickType, Player player)
	{
		tryFunctions(slot, clickType, player, 0);
	}

	public static void tryFunctions(Slot slot, ClickType clickType, Player player, int startingIndex)
	{
		FunctionResponse result = null;
		if(slot.getFunctions() != null)
		{
			result = tryFunctions(player, slot.getFunctions(), slot, startingIndex);
			if(!result.result)
			{
				ArrayList<Function> failFunctions = slot.getFailFunctions(result.failedFunction);
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
				ArrayList<Function> failFunctions = slot.getLeftClickFailFunctions(result.failedFunction);
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
				ArrayList<Function> failFunctions = slot.getRightClickFailFunctions(result.failedFunction);
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
				ArrayList<Function> failFunctions = slot.getMiddleClickFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(player, failFunctions, slot, startingIndex);
				}

				return;
			}
		}
	}

	private static FunctionResponse tryFunctions(Player player, ArrayList<Function> functions, Slot slot, int startingIndex)
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


/*
for(int i = startingIndex; i < functions.size(); i++)
		{
			Function func = functions.get(i);
			//System.out.println(func.getName() + ": " + func.getData());
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
				ArrayList<Function> failFunctions = slot.getFailFunctions(myFunc.getName());
				if(failFunctions != null)
				{
					for(int j = 0; j < failFunctions.size(); j++)
					{
						Function fail = failFunctions.get(j);
						if(FunctionApi.getFunctionByName(fail.getName()) == null)
							continue;
						try 
						{
							Function failFunction = FunctionApi.getFunctionByName(fail.getName()).getClass().getConstructor(String.class).newInstance(fail.getName());
	
							failFunction.setData(fail.getData());
							failFunction.setOwner(slot);
							failFunction.setIndex(i);
							boolean failResult = failFunction.function(player);
							if(!failResult)
							{
								break;
							}
						} 
						catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1) 
						{
							e1.printStackTrace();
							break;
						}

					}
				}
				return;
			}
		}





*/