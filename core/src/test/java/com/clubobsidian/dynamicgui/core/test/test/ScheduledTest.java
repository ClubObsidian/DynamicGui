package com.clubobsidian.dynamicgui.core.test.test;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.scheduler.Scheduler;
import com.clubobsidian.dynamicgui.core.test.mock.scheduler.MockScheduler;
import org.junit.jupiter.api.AfterEach;

public interface ScheduledTest extends FactoryTest {

    @AfterEach
    default void stop() {
        Scheduler scheduler = DynamicGui.get().getPlatform().getScheduler();
        if (scheduler != null) {
            ((MockScheduler) scheduler).stop();
        }
    }
}
