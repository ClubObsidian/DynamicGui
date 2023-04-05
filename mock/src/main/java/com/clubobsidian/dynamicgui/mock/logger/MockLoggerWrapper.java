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

package com.clubobsidian.dynamicgui.mock.logger;

import com.clubobsidian.dynamicgui.api.logger.LoggerWrapper;

public class MockLoggerWrapper implements LoggerWrapper<MockLogger> {

    private final MockLogger logger;

    public MockLoggerWrapper(MockLogger logger) {
        this.logger = logger;
    }

    @Override
    public MockLogger getLogger() {
        return this.logger;
    }

    @Override
    public void info(String message) {
        this.getLogger().info(message);
    }

    @Override
    public void error(String message) {
        this.getLogger().error(message);
    }
}
