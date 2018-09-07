/*   Copyright 2018 Club Obsidian and contributors.

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
package com.clubobsidian.dynamicgui.scheduler.sponge;

import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;

import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.scheduler.Scheduler;

public class SpongeScheduler extends Scheduler {
	
	@Override
	public void scheduleSyncDelayedTask(DynamicGuiPlugin plugin, Runnable runnable, Long delay) 
	{
		delay = this.ticksToMillis(delay); 
		Sponge.getScheduler().createSyncExecutor(plugin).schedule(runnable, delay, TimeUnit.MILLISECONDS);
	}

	
	@Override
	public void scheduleSyncRepeatingTask(DynamicGuiPlugin plugin, Runnable runnable, Long delayInitial, Long delayRepeating) 
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
