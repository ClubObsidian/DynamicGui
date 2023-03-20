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

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface LoggerWrapper<T> {

    /**
     * Gets the native underlying logger object that
     * this wrapper wraps.
     *
     * @return the native logger
     */
    T getLogger();

    /**
     * Logs the given formatted info message
     * using String.format(message, args)
     *
     * @param message the message to log
     * @param args the args to log
     */
    default void info(@NotNull String message, @NotNull Object... args) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(args);
        this.info(String.format(message, args));
    }

    /**
     * Logs the given info message
     *
     * @param message the message to log
     */
    void info(@NotNull String message);

    /**
     * Logs the given formatted error message
     * using String.format(message, args)
     *
     * @param message the message to log
     * @param args the args to log
     */
    default void error(@NotNull String message, @NotNull Object... args) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(args);
        this.error(String.format(message, args));
    }

    /**
     * Logs the given message error message
     *
     * @param message the message to log
     */
    void error(String message);
}