package com.clubobsidian.dynamicgui.util;

public class ClassUtil {

	public static Class<?> getClassType(String data)
	{	
		if(data.toLowerCase().equals("true") || data.toLowerCase().equals("false"))
		{
			return boolean.class;
		}
		
		try
		{
			Integer.parseInt(data);
			return Integer.TYPE;
		}
		catch(Exception ex) {}
		
		try
		{
			Double.parseDouble(data);
			return Double.TYPE;
		}
		catch(Exception ex) {}
		
		return String.class;
	}	
}