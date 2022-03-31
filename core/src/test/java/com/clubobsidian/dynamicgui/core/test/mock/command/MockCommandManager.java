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

package com.clubobsidian.dynamicgui.core.test.mock.command;

import cloud.commandframework.CommandManager;
import cloud.commandframework.CommandTree;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.internal.CommandRegistrationHandler;
import cloud.commandframework.meta.CommandMeta;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Function;

public class MockCommandManager<C> extends CommandManager<C> {

    public MockCommandManager() {
        this(CommandExecutionCoordinator.simpleCoordinator(),
                CommandRegistrationHandler.nullCommandRegistrationHandler());
    }

    /**
     * Create a new command manager instance
     *
     * @param commandExecutionCoordinator Execution coordinator instance. The coordinator is in charge of executing incoming
     *                                    commands. Some considerations must be made when picking a suitable execution coordinator
     *                                    for your platform. For example, an entirely asynchronous coordinator is not suitable
     *                                    when the parsers used in that particular platform are not thread safe. If you have
     *                                    commands that perform blocking operations, however, it might not be a good idea to
     *                                    use a synchronous execution coordinator. In most cases you will want to pick between
     *                                    {@link CommandExecutionCoordinator#simpleCoordinator()} and
     *                                    {@link AsynchronousCommandExecutionCoordinator}
     * @param commandRegistrationHandler  Command registration handler. This will get called every time a new command is
     *                                    registered to the command manager. This may be used to forward command registration
     */
    protected MockCommandManager(@NonNull Function<@NonNull CommandTree<C>, @NonNull CommandExecutionCoordinator<C>> commandExecutionCoordinator, @NonNull CommandRegistrationHandler commandRegistrationHandler) {
        super(commandExecutionCoordinator, commandRegistrationHandler);
    }

    @Override
    public boolean hasPermission(@NonNull C sender, @NonNull String permission) {
        return false;
    }

    @Override
    public @NonNull CommandMeta createDefaultCommandMeta() {
        return null;
    }
}
