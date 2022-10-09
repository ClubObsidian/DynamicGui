/*
 *    Copyright 2022 virustotalop and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clubobsidian.dynamicgui.bukkit.scheduler;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.api.scheduler.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BukkitScheduler implements Scheduler {

    @Override
    public void runSyncDelayedTask(Runnable runnable, long delay) {
        Bukkit.getScheduler()
                .scheduleSyncDelayedTask((Plugin) DynamicGui.get().getPlugin(), runnable, delay);
    }

    @Override
    public void runAsynchronousDelayedTask(Runnable runnable, long delay) {
        Bukkit.getScheduler()
                .runTaskLaterAsynchronously((Plugin) DynamicGui.get().getPlugin(), runnable, delay);
    }

    @Override
    public void scheduleSyncRepeatingTask(Runnable runnable, long delayInitial, long delayRepeating) {
        Bukkit.getScheduler()
                .scheduleSyncRepeatingTask((Plugin) DynamicGui.get().getPlugin(), runnable, delayInitial, delayRepeating);
    }

    @Override
    public void scheduleAsyncRepeatingTask(Runnable runnable, long delayInitial, long delayRepeating) {
        Bukkit.getServer().getScheduler()
                .runTaskTimerAsynchronously((Plugin) DynamicGui.get().getPlugin(),
                        runnable, delayInitial, delayRepeating);
    }
}