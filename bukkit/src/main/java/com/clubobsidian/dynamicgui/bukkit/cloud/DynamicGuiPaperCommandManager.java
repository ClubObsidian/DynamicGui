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

package com.clubobsidian.dynamicgui.bukkit.cloud;

import com.clubobsidian.dynamicgui.core.util.ReflectionUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.CloudCapability;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.LegacyPaperCommandManager;
import org.incendo.cloud.paper.PaperCommandManager;

import java.util.Set;
import java.util.function.Function;

public class DynamicGuiPaperCommandManager<C> extends LegacyPaperCommandManager<C> {

    //A hack for not updating the command map for each player every time a new command is registered

    private final Set<CloudCapability> capabilities;

    public DynamicGuiPaperCommandManager(final @NonNull Plugin owningPlugin,
                                         final @NonNull ExecutionCoordinator<C> commandExecutionCoordinator,
                                         final @NonNull SenderMapper<CommandSender, C> senderMapper) throws Exception {
        super(owningPlugin, commandExecutionCoordinator, senderMapper);
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
