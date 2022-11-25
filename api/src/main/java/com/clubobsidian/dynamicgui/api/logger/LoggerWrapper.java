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

package com.clubobsidian.dynamicgui.api.logger;

public abstract class LoggerWrapper<T> {

    private final T logger;

    public LoggerWrapper(T logger) {
        this.logger = logger;
    }

    /**
     * Gets the native underlying logger object that
     * this wrapper wraps.
     *
     * @return the native logger
     */
    public T getLogger() {
        return this.logger;
    }

    /**
     * Logs the given info message
     *
     * @param message the message to log
     */
    public abstract void info(String message);

    /**
     * Logs the given message error message
     *
     * @param message the message to log
     */
    public abstract void error(String message);
}