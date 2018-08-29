package com.clubobsidian.dynamicgui.scheduler;

import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;

public abstract class Scheduler {

	public abstract void scheduleSyncDelayedTask(DynamicGuiPlugin plugin, Runnable runnable, Long delay);
	public abstract void scheduleSyncRepeatingTask(DynamicGuiPlugin plugin, Runnable runnable, Long delayInitial, Long delayRepeating);
	
}