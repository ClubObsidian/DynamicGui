package com.clubobsidian.dynamicgui.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.clubobsidian.dynamicgui.function.Function;

public class FunctionApi {
	
	private static Map<String, Function> functions = new HashMap<>();
	
	public static Function getFunctionByName(String name)
	{
		return functions.get(name);
	}
	
	public static ArrayList<Function> getFunctions()
	{
		ArrayList<Function> funcs = new ArrayList<Function>();
		funcs.addAll(functions.values());
		return funcs;
	}
	
	public static void addFunction(Function func)
	{
		functions.put(func.getName(), func);
	}

	public static String[] parseData(String data)
	{
		String[] ar = new String[2];
		String dat = null;
		String[] args = data.split(":");
		
		if(data.charAt(data.indexOf(":") + 1) == ' ')
		{
			dat = args[1].substring(1);
		}
		else 
		{
			dat = args[1];
		}
		if(args.length > 2)
		{
			for(int i = 2; i < args.length; i++)
				dat +=  ":" + args[i];
		}
		
		//System.out.println("_" + args[0] + "_");
		ar[0] = args[0];
		ar[1] = dat;
		return ar;	
	}
}
