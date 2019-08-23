/*
   Copyright 2019 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.dynamicgui.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectionUtil {

	private ReflectionUtil() {}
	
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
	
	public static Method findMethodByReturnType(Class<?> searchIn, Class<?> returnType)
	{
		for(Method m : searchIn.getDeclaredMethods())
		{
			if(m.getReturnType() == returnType)
			{
				m.setAccessible(true);
				return m;
			}
		}
		return null;
	}
	
	public static Field getFieldByName(Class<?> searchIn, String name)
	{
		try 
		{
			Field f = searchIn.getDeclaredField(name);
			f.setAccessible(true);
			return f;
		} 
		catch (NoSuchFieldException | SecurityException e) 
		{
			return null;
		}
	}
	
	public static Field getFieldByType(Class<?> searchIn, Class<?> type)
	{
		for(Field f : searchIn.getDeclaredFields())
		{
			if(f.getType() == type)
			{
				f.setAccessible(true);
				return f;
			}
		}
		return null;
	}
	
	public static class ReflectionHelper<T> {
		
		public T get(Field field)
		{
			return this.get(field, null);
		}
		
		@SuppressWarnings("unchecked")
		public T get(Field field, Object getFromClass)
		{
			try 
			{
				return (T) field.get(getFromClass);
			} 
			catch (IllegalArgumentException | IllegalAccessException e) 
			{
				return null;
			}
		}
	}
}