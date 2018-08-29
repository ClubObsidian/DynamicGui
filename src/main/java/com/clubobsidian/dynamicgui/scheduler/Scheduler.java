package com.clubobsidian.dynamicgui.scheduler;

import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin2;

public abstract class Scheduler {

	public abstract void scheduleSyncDelayedTask(DynamicGuiPlugin2 plugin, Runnable runnable, Long delay);
	public abstract void scheduleSyncRepeatingTask(DynamicGuiPlugin2 plugin, Runnable runnable, Long delayInitial, Long delayRepeating);
	
}