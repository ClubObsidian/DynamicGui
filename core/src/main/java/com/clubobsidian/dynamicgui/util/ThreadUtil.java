package com.clubobsidian.dynamicgui.util;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.scheduler.Scheduler;
import com.clubobsidian.dynamicgui.server.FakeServer;

public final class ThreadUtil {

    public static void run(Runnable runnable, boolean isAsync) {
        FakeServer server = DynamicGui.get().getServer();
        Scheduler scheduler = server.getScheduler();
        if(isAsync) {
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
