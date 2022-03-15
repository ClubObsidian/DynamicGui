package com.clubobsidian.dynamicgui.core.test.mock;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.scheduler.Scheduler;
import com.clubobsidian.dynamicgui.core.test.mock.scheduler.MockScheduler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public interface ScheduledTest {

    MockFactory factory = new MockFactory();

    default MockFactory getFactory() {
        return factory;
    }

    @BeforeEach
    default void setup() {
        factory.inject();
    }

    @AfterEach
    default void stop() {
        Scheduler scheduler = DynamicGui.get().getPlatform().getScheduler();
        if(scheduler != null) {
            ((MockScheduler)scheduler).stop();
        }
    }
}
