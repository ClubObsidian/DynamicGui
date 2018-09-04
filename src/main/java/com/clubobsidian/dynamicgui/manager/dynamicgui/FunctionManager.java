package com.clubobsidian.dynamicgui.manager.dynamicgui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clubobsidian.dynamicgui.function.Function;

public class FunctionManager {
	
	private static FunctionManager instance;
	
	private Map<String, Function> functions;
	private FunctionManager()
	{
		this.functions = new HashMap<>();
	}
	
	public static FunctionManager get()
	{
		if(instance == null)
		{
			instance = new FunctionManager();
		}
		return instance;
	}
	
	public Function getFunctionByName(String name)
	{
		return this.functions.get(name);
	}
	
	public List<Function> getFunctions()
	{
		List<Function> funcs = new ArrayList<>();
		funcs.addAll(this.functions.values());
		return funcs;
	}
	
	public void addFunction(Function func)
	{
		this.functions.put(func.getName(), func);
	}

	public String[] parseData(String data)
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
		
		ar[0] = args[0];
		ar[1] = dat;
		return ar;	
	}
}
