package com.clubobsidian.dynamicgui.core.util;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.scheduler.Scheduler;
import com.clubobsidian.dynamicgui.core.server.Platform;

public final class ThreadUtil {

    public static void run(Runnable runnable, boolean async) {
        Platform server = DynamicGui.get().getPlatform();
        Scheduler scheduler = server.getScheduler();
        if(async) {
            scheduler.runAsynchronousDelayedTask(runnable, 0);
        } else {
            if(!server.isMainThread()) {
                scheduler.runSyncDelayedTask(runnable, 0);
            } else {
                runnable.run();
            }
        }
    }

    private ThreadUtil() {
    }
}
