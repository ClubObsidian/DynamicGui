package com.clubobsidian.dynamicgui.scheduler.sponge;

import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;

import com.clubobsidian.dynamicgui.plugin.DynamicGUIPlugin;
import com.clubobsidian.dynamicgui.scheduler.Scheduler;

public class SpongeScheduler extends Scheduler {
	
	@Override
	public void scheduleSyncDelayedTask(DynamicGUIPlugin plugin, Runnable runnable, Long delay) 
	{
		delay = this.ticksToMillis(delay); 
		Sponge.getScheduler().createSyncExecutor(plugin).schedule(runnable, delay, TimeUnit.MILLISECONDS);
	}

	
	@Override
	public void scheduleSyncRepeatingTask(DynamicGUIPlugin plugin, Runnable runnable, Long delayInitial, Long delayRepeating) 
	{
		delayInitial = this.ticksToMillis(delayInitial);
		delayRepeating = this.ticksToMillis(delayRepeating);
		Sponge.getScheduler().createSyncExecutor(plugin).scheduleAtFixedRate(runnable, delayInitial, delayRepeating, TimeUnit.MILLISECONDS);
	}
	
	//Convert from ticks to milliseconds
	private Long ticksToMillis(Long delay)
	{
		return delay *= 50;
	}
}