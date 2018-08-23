package com.clubobsidian.dynamicgui.scheduler.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.clubobsidian.dynamicgui.plugin.DynamicGUIPlugin;
import com.clubobsidian.dynamicgui.scheduler.Scheduler;

public class BukkitScheduler extends Scheduler {

	@Override
	public void scheduleSyncDelayedTask(DynamicGUIPlugin<?, ?> plugin, Runnable runnable, Long delay) 
	{
		Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) plugin, runnable, delay);
	}

	@Override
	public void scheduleSyncRepeatingTask(DynamicGUIPlugin<?, ?> plugin, Runnable runnable, Long delayInitial, Long delayRepeating) 
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) plugin, runnable, delayInitial, delayRepeating);
	}
}