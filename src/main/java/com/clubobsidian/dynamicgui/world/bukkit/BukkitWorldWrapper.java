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
package com.clubobsidian.dynamicgui.world.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.clubobsidian.dynamicgui.world.WorldWrapper;

public class BukkitWorldWrapper extends WorldWrapper<World> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3743616842652828642L;

	public BukkitWorldWrapper(String name) 
	{
		super(name);
	}

	@Override
	public World getWorld() 
	{
		return Bukkit.getServer().getWorld(this.getName());
	}

	@Override
	public void setGameRule(String rule, String value) 
	{
		this.getWorld().setGameRuleValue(rule, value);
	}

	@Override
	public String getGameRule(String rule) 
	{
		return this.getWorld().getGameRuleValue(rule);
	}
}