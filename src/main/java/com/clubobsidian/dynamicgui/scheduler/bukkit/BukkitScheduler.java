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
package com.clubobsidian.dynamicgui.scheduler.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.scheduler.Scheduler;

public class BukkitScheduler extends Scheduler {

	@Override
	public void scheduleSyncDelayedTask(DynamicGuiPlugin plugin, Runnable runnable, Long delay) 
	{
		Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) plugin, runnable, delay);
	}

	@Override
	public void scheduleSyncRepeatingTask(DynamicGuiPlugin plugin, Runnable runnable, Long delayInitial, Long delayRepeating) 
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) plugin, runnable, delayInitial, delayRepeating);
	}
}