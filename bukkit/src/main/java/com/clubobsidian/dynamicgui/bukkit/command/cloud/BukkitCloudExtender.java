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

package com.clubobsidian.dynamicgui.bukkit.command.cloud;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.bukkit.BukkitPluginRegistrationHandler;
import cloud.commandframework.internal.CommandRegistrationHandler;
import com.clubobsidian.dynamicgui.core.command.cloud.CloudExtender;
import com.clubobsidian.dynamicgui.core.util.ReflectionUtil;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BukkitCloudExtender implements CloudExtender {

    @Override
    public boolean unregister(CommandManager commandManager, Command command, String alias) {
        BukkitPluginRegistrationHandler handler = (BukkitPluginRegistrationHandler)
                commandManager.getCommandRegistrationHandler();
        return this.unregisterHandler(handler, alias)
                || this.unregisterAliases(handler, alias)
                || this.unregisterBukkitCommand(handler, alias);
    }

    private boolean unregisterBukkitCommand(BukkitPluginRegistrationHandler handler, String alias) {
        Map<String, org.bukkit.command.Command> bukkitCommands =
                ReflectionUtil.get(handler, handler.getClass(), "bukkitCommands");
        return bukkitCommands.remove(alias) != null;
    }

    private boolean unregisterHandler(CommandRegistrationHandler handler, String alias) {
        Map<CommandArgument<?, ?>, org.bukkit.command.Command> registeredCommands =
                ReflectionUtil.get(handler, BukkitPluginRegistrationHandler.class, "registeredCommands");
        Iterator<Map.Entry<CommandArgument<?, ?>, org.bukkit.command.Command>> it = registeredCommands.entrySet().iterator();
        boolean removed = false;
        while (it.hasNext()) {
            org.bukkit.command.Command command = it.next().getValue();
            if(command.getName().equals(alias) || command.getAliases().contains(alias)) {
                it.remove();
                removed = true;
            }
        }
        return removed;
    }

    private boolean unregisterAliases(BukkitPluginRegistrationHandler handler, String alias) {
        Set<String> aliases = ReflectionUtil.get(handler, handler.getClass(), "recognizedAliases");
        return aliases.remove(alias);
    }
}