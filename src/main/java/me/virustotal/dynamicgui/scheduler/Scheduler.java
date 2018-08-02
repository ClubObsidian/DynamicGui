package me.virustotal.dynamicgui.scheduler;

import me.virustotal.dynamicgui.plugin.DynamicGUIPlugin;

public abstract class Scheduler {

	public abstract void scheduleSyncDelayedTask(DynamicGUIPlugin<?,?> plugin, Runnable runnable, Long delay);
	
}
