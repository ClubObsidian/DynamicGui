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

package com.clubobsidian.dynamicgui.bukkit.cloud;

import cloud.commandframework.CloudCapability;
import cloud.commandframework.CommandManager;
import cloud.commandframework.CommandTree;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import com.clubobsidian.dynamicgui.core.util.ReflectionUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Set;
import java.util.function.Function;

public class DynamicGuiPaperCommandManager<C> extends PaperCommandManager<C> {

    //A hack for not updating the command map for each player every time a new command is registered

    private final Set<CloudCapability> capabilities;

    public DynamicGuiPaperCommandManager(@NonNull Plugin owningPlugin,
                                         @NonNull Function<CommandTree<C>,
                                                 CommandExecutionCoordinator<C>> commandExecutionCoordinator,
                                         @NonNull Function<CommandSender, C> commandSenderMapper,
                                         @NonNull Function<C, CommandSender> backwardsCommandSenderMapper) throws Exception {
        super(owningPlugin, commandExecutionCoordinator, commandSenderMapper, backwardsCommandSenderMapper);
        this.capabilities = (Set<CloudCapability>) ReflectionUtil
                .getFieldByName(CommandManager.class, "capabilities")
                .get(this);
    }

    @Override
    public void deleteRootCommand(final @NonNull String rootCommand)
            throws CloudCapability.CloudCapabilityMissingException {
        boolean removed = this.capabilities.remove(CloudBukkitCapabilities.BRIGADIER);
        super.deleteRootCommand(rootCommand);
        if (removed) {
            this.capabilities.add(CloudBukkitCapabilities.BRIGADIER);
        }
    }
}
