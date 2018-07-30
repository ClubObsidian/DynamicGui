package me.virustotal.dynamicgui.objects.function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.objects.Function;
import me.virustotal.dynamicgui.utils.ClassUtil;

public class ReflectionStaticFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6800686646833695589L;

	public ReflectionStaticFunction(String name) 
	{
		super(name);
	}

	//reflection: me.virustotal.test.Test.moreThanOrEqualToFive,%player-object%;%player-name%)
	
	
	@Override
	public boolean function(Player player)
	{
		String className = null;
		String methodName = null;
		Object[] objectArray = null;
		Class<?>[] classArray = null;
		if(this.getData().contains(","))
		{
			String[] args = this.getData().split(",");
			String[] strObjs = args[1].split(";");
			ArrayList<Object> objects = new ArrayList<Object>();
			ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
			for(String str : strObjs)
			{
				if(str.equals("%player-object%"))
				{
					objects.add(player);
					classes.add(Player.class);
				}
				else if(str.equals("%player-name%"))
				{
					objects.add(player.getName());
					classes.add(String.class);
				}
				else
				{
					Class<?> cl = ClassUtil.getClassType(str);
					classes.add(cl);
					if(cl.equals(String.class))
					{
						objects.add(str);
					}
					else if(cl.equals(Integer.TYPE))
					{
						objects.add(Integer.parseInt(str));
					}
					else if(cl.equals(Boolean.TYPE))
					{
						objects.add(Boolean.parseBoolean(str));
					}
					else if(cl.equals(Double.TYPE))
					{
						objects.add(Double.parseDouble(str));
					}
				}
			}
			String[] classData = args[0].split("\\.");
			methodName = classData[classData.length - 1];
			String name = classData[classData.length - 2];
			className = this.getData().substring(0, this.getData().indexOf(name)) + name;
			objectArray = objects.toArray(new Object[objects.size()]);
			classArray = classes.toArray(new Class<?>[classes.size()]);
		}
		else
		{
			String[] classData = this.getData().split("\\.");
			methodName = classData[classData.length - 1];
			String name = classData[classData.length - 2];
			className = this.getData().substring(0, this.getData().indexOf(name)) + name;
		}
		
		try 
		{
			Class<?> cl = Class.forName(className);
			Method meth = null;
			if(classArray != null)
			{
				for(Method m : cl.getDeclaredMethods())
				{
					if(m.getName().equals(methodName))
					{
						if(m.getParameters().length == classArray.length)
						{
							for(int i = 0; i < m.getParameterTypes().length; i++)
							{
								Class<?> c = m.getParameterTypes()[i];
								if(c.equals(Boolean.TYPE))
								{
									if(classArray[i].equals(boolean.class))
									{
										classArray[i] = Boolean.TYPE;
										objectArray[i] = new Boolean((boolean) objectArray[i]);
									}
								}
								else if(c.equals(Integer.TYPE))
								{
									if(classArray[i].equals(int.class))
									{
										classArray[i] = Integer.TYPE;
										objectArray[i] = new Integer((int) objectArray[i]);
									}
								}
								else if(c.equals(Double.TYPE))
								{
									if(classArray[i].equals(double.class))
									{
										classArray[i] = Double.TYPE;
										objectArray[i] = new Double((double) objectArray[i]);
									}
								}
							}
							meth = m;
						}
					}
				}
			}
			if(meth == null)
			{
				meth = cl.getDeclaredMethod(methodName, classArray);
			}
			meth.setAccessible(true);
			Object invoked = meth.invoke(null, objectArray);
			if(invoked instanceof Boolean)
			{
				return (boolean) invoked;
			}
			return true;
		} 
		catch (ClassNotFoundException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) 
		{
			e.printStackTrace();
		}
		
		return false;		
	}
	
}
