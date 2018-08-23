package com.clubobsidian.dynamicgui.scheduler;

import com.clubobsidian.dynamicgui.plugin.DynamicGUIPlugin;

public abstract class Scheduler {

	public abstract void scheduleSyncDelayedTask(DynamicGUIPlugin<?,?> plugin, Runnable runnable, Long delay);

	public abstract void scheduleSyncRepeatingTask(DynamicGUIPlugin<?, ?> plugin, Runnable runnable, Long delayInitial, Long delayRepeating);
	
}