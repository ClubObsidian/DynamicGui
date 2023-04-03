package com.clubobsidian.dynamicgui.parser.test.mock;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.mock.logger.MockLogger;
import com.clubobsidian.dynamicgui.mock.logger.MockLoggerWrapper;

public abstract class MockDynamicGui extends DynamicGui {

    private final LoggerWrapper<?> logger = new MockLoggerWrapper(new MockLogger());

    @Override
    public LoggerWrapper<?> getLogger() {
        return this.logger;
    }
}
