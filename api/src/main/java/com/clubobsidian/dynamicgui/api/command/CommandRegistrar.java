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

package com.clubobsidian.dynamicgui.api.command;

import org.jetbrains.annotations.NotNull;

public interface CommandRegistrar {

    /**
     * Registers a built-in DynamicGui command
     *
     * @param command command that is registered
     */
    void registerCommand(@NotNull Class<? extends RegisteredCommand> command);

    /**
     * Registers a command alias for a gui
     *
     * @param guiName the name of the gui that an alias is being registered for
     * @param alias   the alias that should be registered
     */
    void registerGuiAliasCommand(@NotNull String guiName, @NotNull String alias);

    /**
     * Unregisters a command
     *
     * @param alias the command alias to unregister
     */
    void unregisterCommand(@NotNull String alias);

    /**
     * Unregisters all gui aliases
     */
    void unregisterGuiAliases();

}