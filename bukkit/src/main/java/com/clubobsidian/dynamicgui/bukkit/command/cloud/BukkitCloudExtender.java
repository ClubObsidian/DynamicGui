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
import com.clubobsidian.dynamicgui.core.Key;
import com.clubobsidian.dynamicgui.core.command.cloud.CloudExtender;
import com.clubobsidian.dynamicgui.core.util.ReflectionUtil;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BukkitCloudExtender implements CloudExtender {

    private final boolean brigadierEnabled = this.checkForBrigadier();

    @Override
    public boolean unregister(CommandManager commandManager, Command command, String alias) {
        BukkitPluginRegistrationHandler handler = (BukkitPluginRegistrationHandler)
                commandManager.getCommandRegistrationHandler();
        boolean handlerUnregistered = this.unregisterHandler(handler, alias);
        boolean aliasesUnregistered = this.unregisterAliases(handler, alias);
        boolean bukkitUnregistered = this.unregisterBukkitCommand(handler, alias);
        boolean brigadierUnregistered =  this.unregisterBrigadier(alias);
        return handlerUnregistered || aliasesUnregistered || bukkitUnregistered || brigadierUnregistered;
    }

    private boolean checkForBrigadier() {
        try {
            Class.forName("com.mojang.brigadier.CommandDispatcher");
            return true;
        } catch(ClassNotFoundException ex) {
            return false;
        }
    }

    private boolean unregisterBukkitCommand(BukkitPluginRegistrationHandler handler, String alias) {
        Map<String, org.bukkit.command.Command> bukkitCommands =
                ReflectionUtil.get(handler, handler.getClass(), "bukkitCommands");
        boolean removed = bukkitCommands.remove(alias) != null;
        boolean namespaceRemoved = bukkitCommands.remove(Key.create(alias).toString()) != null;
        return removed || namespaceRemoved;
    }

    private boolean unregisterHandler(CommandRegistrationHandler handler, String alias) {
        Map<CommandArgument<?, ?>, org.bukkit.command.Command> registeredCommands =
                ReflectionUtil.get(handler, BukkitPluginRegistrationHandler.class, "registeredCommands");
        Iterator<Map.Entry<CommandArgument<?, ?>, org.bukkit.command.Command>> it = registeredCommands.entrySet().iterator();
        boolean removed = false;
        String namespaceAlias = Key.create(alias).toString();
        while (it.hasNext()) {
            org.bukkit.command.Command command = it.next().getValue();
            if(command.getName().equals(alias)
                    || command.getName().equals(namespaceAlias)
                    || command.getAliases().contains(alias)
                    || command.getAliases().contains(namespaceAlias)
                    || command.getLabel().equals(alias)
                    || command.getLabel().equals(namespaceAlias)) {
                it.remove();
                removed = true;
            }
        }
        return removed;
    }

    private boolean unregisterAliases(BukkitPluginRegistrationHandler handler, String alias) {
        Set<String> aliases = ReflectionUtil.get(handler, handler.getClass(), "recognizedAliases");
        boolean original = aliases.remove(alias);
        boolean namespace = aliases.remove(Key.create(alias).toString());
        return original || namespace;
    }

    private boolean unregisterBrigadier(String alias) {
        if (this.brigadierEnabled) {
            //TODO - implement unregistering brigadier
        }
        return false;
    }
}