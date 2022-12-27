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

package com.clubobsidian.dynamicgui.core.logger;

import com.clubobsidian.dynamicgui.api.logger.LoggerWrapper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaLoggerWrapper<T extends Logger>implements  LoggerWrapper<T> {

    private final T logger;

    public JavaLoggerWrapper(T logger) {
        this.logger = logger;
    }

    @Override
    public T getLogger() {
        return this.logger;
    }

    @Override
    public void info(String message) {
        this.getLogger().log(Level.INFO, message);
    }

    @Override
    public void error(String message) {
        this.getLogger().log(Level.SEVERE, message);
    }
}