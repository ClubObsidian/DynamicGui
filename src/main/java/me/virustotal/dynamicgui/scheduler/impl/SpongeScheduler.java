package me.virustotal.dynamicgui.scheduler.impl;

import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;

import me.virustotal.dynamicgui.plugin.DynamicGUIPlugin;
import me.virustotal.dynamicgui.scheduler.Scheduler;

public class SpongeScheduler extends Scheduler {
	
	@Override
	public void scheduleSyncDelayedTask(DynamicGUIPlugin<?, ?> plugin, Runnable runnable, Long delay) 
	{
		delay = this.ticksToMillis(delay); 
		Sponge.getScheduler().createSyncExecutor(plugin).schedule(runnable, delay, TimeUnit.MILLISECONDS);
	}

	//Convert from ticks to milliseconds
	private Long ticksToMillis(Long delay)
	{
		return delay *= 50;
	}
}
