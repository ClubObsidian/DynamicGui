package me.virustotal.dynamicgui.scheduler.impl;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import me.virustotal.dynamicgui.plugin.DynamicGUIPlugin;
import me.virustotal.dynamicgui.scheduler.Scheduler;

public class BukkitScheduler extends Scheduler {

	@Override
	public void scheduleSyncDelayedTask(DynamicGUIPlugin<?, ?> plugin, Runnable runnable, Long delay) 
	{
		Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) plugin, runnable, delay);
	}
}