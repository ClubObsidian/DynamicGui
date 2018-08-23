package com.clubobsidian.dynamicgui.util;

import java.lang.reflect.Method;

public class ReflectionUtil {

	public static Class<?> classForName(String name)
	{
		try 
		{
			return Class.forName(name);
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	public static Method getMethod(Class<?> cl, String methodName) 
	{
		try 
		{
			Method method = cl.getDeclaredMethod(methodName);
			method.setAccessible(true);
			return method;
		} 
		catch (NoSuchMethodException | SecurityException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static Method getMethod(Class<?> cl, String methodName, Class<?>...params)
	{
		try 
		{
			Method method = cl.getDeclaredMethod(methodName, params);
			method.setAccessible(true);
			return method;
		} 
		catch (NoSuchMethodException | SecurityException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}