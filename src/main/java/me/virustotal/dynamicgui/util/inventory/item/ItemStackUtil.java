package me.virustotal.dynamicgui.util.inventory.item;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.virustotal.dynamicgui.inventory.ItemStackWrapper;
import me.virustotal.dynamicgui.server.ServerType;

/**
 * 
 * @author virustotal
 *
 * Generates an ItemStack or ItemStackWrapper
 * based on the current runtime. Currently
 * only supports simple items without any extra
 * metadata. Subject to change in the future.
 */
public final class ItemStackUtil {

	private static Class<?> bukkitMaterialClass;
	private static Class<?> bukkitItemStackClass;

	private static Method bukkitMaterialValueOf;
	private static Constructor<?> bukkitItemStackConstructor;

	private static Class<?> spongeItemStackClass;
	private static Class<?> spongeBuilderClass;
	private static Class<?> spongeItemTypeClass;
	private static Class<?> spongeItemTypesClass;
	
	private static Method spongeBuilderMethod;
	private static Method spongeBuilderItemTypeMethod;
	private static Method spongeBuilderQuantityMethod;
	private static Method spongeBuilderBuildMethod;
	
	private static Class<?> bukkitItemStackWrapperClass;
	private static Constructor<?> bukkitItemStackWrapperConstructor;
	
	private static Class<?> spongeItemStackWrapperClass;
	private static Constructor<?> spongeItemStackWrapperConstructor;
	
	private ItemStackUtil() {}

	public static Object createItemStack(String type, int quantity)
	{

		if(ServerType.SPIGOT.equals(ServerType.get()))
		{
			try 
			{
				if(bukkitMaterialClass == null)
				{
					bukkitMaterialClass = Class.forName("org.bukkit.Material");
				}
				if(bukkitItemStackClass == null)
				{
					bukkitItemStackClass = Class.forName("org.bukkit.inventory.ItemStack");
				}

				if(bukkitMaterialValueOf == null)
				{
					bukkitMaterialValueOf = bukkitMaterialClass.getDeclaredMethod("valueOf", String.class);
					bukkitMaterialValueOf.setAccessible(true);
				}

				if(bukkitItemStackConstructor == null)
				{
					bukkitItemStackConstructor = bukkitItemStackClass.getDeclaredConstructor(bukkitMaterialClass, int.class);
					bukkitItemStackConstructor.setAccessible(true);
				}

				Object mat = bukkitMaterialValueOf.invoke(null, type);
				return bukkitItemStackConstructor.newInstance(mat, quantity);
			} 
			catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) 
			{
				e.printStackTrace();
			}
		}
		else if(ServerType.SPONGE.equals(ServerType.get()))
		{
			try 
			{
				if(spongeItemStackClass == null)
				{
					spongeItemStackClass = Class.forName("org.spongepowered.api.item.inventory.ItemStack");
				}
				
				if(spongeItemTypeClass == null)
				{
					spongeItemTypeClass = Class.forName("org.spongepowered.api.item.ItemType");
				}
				
				if(spongeItemTypesClass == null)
				{
					spongeItemTypesClass = Class.forName("org.spongepowered.api.item.ItemTypes");
				}
					
				if(spongeBuilderMethod == null)
				{
					spongeBuilderMethod = spongeItemStackClass.getDeclaredMethod("builder");
					spongeBuilderMethod.setAccessible(true);
				}
				
				if(spongeBuilderClass == null)
				{
					spongeBuilderClass = spongeBuilderMethod.getReturnType();
				}
				
				if(spongeBuilderItemTypeMethod == null)
				{
					spongeBuilderItemTypeMethod  = spongeBuilderClass.getDeclaredMethod("itemType", spongeItemTypeClass);
					spongeBuilderItemTypeMethod.setAccessible(true);
				}
				
				if(spongeBuilderQuantityMethod == null)
				{
					spongeBuilderQuantityMethod = spongeBuilderClass.getDeclaredMethod("quantity", int.class);
					spongeBuilderQuantityMethod.setAccessible(true);
				}
				
				if(spongeBuilderBuildMethod == null)
				{
					spongeBuilderBuildMethod = spongeBuilderClass.getDeclaredMethod("build");
					spongeBuilderBuildMethod.setAccessible(true);
				}
				
				Object builder = spongeBuilderMethod.invoke(null);
				Object itemType = spongeItemTypesClass.getDeclaredField(type).get(null);
				builder = spongeBuilderItemTypeMethod.invoke(builder, itemType);
				builder = spongeBuilderQuantityMethod.invoke(builder, quantity);
				return spongeBuilderBuildMethod.invoke(builder);
			} 
			catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) 
			{
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public static ItemStackWrapper<?> createItemStackWrapper(String type, int quantity)
	{
		Object itemStack = ItemStackUtil.createItemStack(type, quantity);
		if(ServerType.SPIGOT.equals(ServerType.get()))
		{
			
			try 
			{
				if(bukkitItemStackWrapperClass == null)
				{
					bukkitItemStackWrapperClass = Class.forName("me.virustotal.dynamicgui.inventory.item.impl.BukkitItemStackWrapper");
				}
				if(bukkitItemStackWrapperConstructor == null)
				{
					bukkitItemStackWrapperConstructor = bukkitItemStackWrapperClass.getDeclaredConstructor(bukkitItemStackClass);
				}
				return (ItemStackWrapper<?>) bukkitItemStackWrapperConstructor.newInstance(itemStack);
			} 
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) 
			{
				e.printStackTrace();
			}
		}
		else if(ServerType.SPONGE.equals(ServerType.get()))
		{
			try 
			{
				if(spongeItemStackWrapperClass == null)
				{
					spongeItemStackWrapperClass = Class.forName("me.virustotal.dynamicgui.inventory.item.impl.SpongeItemStackWrapper");
				}
				if(spongeItemStackWrapperConstructor == null)
				{
					spongeItemStackWrapperConstructor = spongeItemStackWrapperClass.getDeclaredConstructor(spongeItemStackClass);
				}
				return (ItemStackWrapper<?>) spongeItemStackWrapperConstructor.newInstance(itemStack);
			} 
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
}
