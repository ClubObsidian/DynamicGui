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
		this.setPlaceHolders = ReflectionUtil.getMethod(this.placeHolderApiClass, "setPlaceholders", OfflinePlayer.class, String.class);
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