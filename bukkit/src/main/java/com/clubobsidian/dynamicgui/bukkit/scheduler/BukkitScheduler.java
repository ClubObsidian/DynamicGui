/*
 *    Copyright 2018-2023 virustotalop
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

import com.clubobsidian.dynamicgui.api.scheduler.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BukkitScheduler implements Scheduler {

    private final Plugin plugin;

    public BukkitScheduler(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void runSyncDelayedTask(@NotNull Runnable runnable, long delay) {
        Objects.requireNonNull(runnable);
        Bukkit.getScheduler()
                .scheduleSyncDelayedTask(this.plugin, runnable, delay);
    }

    @Override
    public void runAsynchronousDelayedTask(@NotNull Runnable runnable, long delay) {
        Objects.requireNonNull(runnable);
        Bukkit.getScheduler()
                .runTaskLaterAsynchronously(this.plugin, runnable, delay);
    }

    @Override
    public void scheduleSyncRepeatingTask(@NotNull Runnable runnable, long delayInitial, long delayRepeating) {
        Objects.requireNonNull(runnable);
        Bukkit.getScheduler()
                .scheduleSyncRepeatingTask(this.plugin, runnable, delayInitial, delayRepeating);
    }

    @Override
    public void scheduleAsyncRepeatingTask(@NotNull Runnable runnable, long delayInitial, long delayRepeating) {
        Objects.requireNonNull(runnable);
        Bukkit.getServer().getScheduler()
                .runTaskTimerAsynchronously(this.plugin,
                        runnable, delayInitial, delayRepeating);
    }
}