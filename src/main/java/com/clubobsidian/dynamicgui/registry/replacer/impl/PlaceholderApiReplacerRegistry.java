package com.clubobsidian.dynamicgui.registry.replacer.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.registry.replacer.ReplacerRegistry;
import com.clubobsidian.dynamicgui.util.ReflectionUtil;

public class PlaceholderApiReplacerRegistry implements ReplacerRegistry {

	private Class<?> placeHolderApiClass;
	private Method setPlaceHolders;
	public PlaceholderApiReplacerRegistry()
	{
		this.placeHolderApiClass = ReflectionUtil.classForName("me.clip.placeholderapi.PlaceholderAPI");
		this.setPlaceHolders = ReflectionUtil.getMethod(placeHolderApiClass, "setPlaceholders", OfflinePlayer.class, String.class);
	}

	@Override
	public String replace(PlayerWrapper<?> playerWrapper, String text) 
	{
		try 
		{
			return (String) this.setPlaceHolders.invoke(null, (Player) playerWrapper.getPlayer(), text);
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}
