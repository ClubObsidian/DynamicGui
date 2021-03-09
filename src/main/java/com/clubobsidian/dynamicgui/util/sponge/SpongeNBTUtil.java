/*
 *    Copyright 2021 Club Obsidian and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.clubobsidian.dynamicgui.util.sponge;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

public final class SpongeNBTUtil {

	private static Method getTagFromJson;
	private static Method toNative;
	private static Field tag;
	private static Method fromNative;
	
	private SpongeNBTUtil() {}
	
	public static Object parse(String nbtStr)
	{
		if(getTagFromJson == null)
		{
			try 
			{
				String parserClassName = "net.minecraft.nbt.JsonToNBT";
				Class<?> jsonToNBT = Class.forName(parserClassName);
				getTagFromJson = jsonToNBT.getDeclaredMethod("func_180713_a", String.class);
				getTagFromJson.setAccessible(true);
			} 
			catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) 
			{
				e.printStackTrace();
			} 
		}
		try
		{
			return getTagFromJson.invoke(null, nbtStr);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	public static ItemStack setTag(ItemStack itemStack, String nbt)
	{
		try
		{
			if(toNative == null)
			{
				toNative = ItemStackUtil.class.getDeclaredMethod("toNative", ItemStack.class);
				toNative.setAccessible(true);
			}
			
			String itemStackClassName = "net.minecraft.item.ItemStack";
			Class<?> nmsItemStackClass = Class.forName(itemStackClassName);
			if(tag == null)
			{	
				tag = nmsItemStackClass.getDeclaredField("field_77990_d");
				tag.setAccessible(true);
			}
			
			if(fromNative == null)
			{
				fromNative = ItemStackUtil.class.getDeclaredMethod("fromNative", nmsItemStackClass);
				fromNative.setAccessible(true);
			}
			
			Object nmsItemStack = toNative.invoke(null, itemStack);
			Object nbtCompound = SpongeNBTUtil.parse(nbt);
			tag.set(nmsItemStack, nbtCompound);
			ItemStack spongeItemStack = (ItemStack) fromNative.invoke(null, nmsItemStack);
			return spongeItemStack;
		}
		catch(NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException | NoSuchFieldException | SecurityException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
}