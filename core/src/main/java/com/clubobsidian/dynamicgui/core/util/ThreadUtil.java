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

package com.clubobsidian.dynamicgui.core.util;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.api.scheduler.Scheduler;

public final class ThreadUtil {

    public static void run(Runnable runnable, boolean async) {
        Platform server = DynamicGui.get().getPlatform();
        Scheduler scheduler = server.getScheduler();
        if (async) {
            scheduler.runAsynchronousDelayedTask(runnable, 0);
        } else {
            if (!server.isMainThread()) {
                scheduler.runSyncDelayedTask(runnable, 0);
            } else {
                runnable.run();
            }
        }
    }

    private ThreadUtil() {
    }
}
