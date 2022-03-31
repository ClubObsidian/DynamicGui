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

package com.clubobsidian.dynamicgui.core.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.CommandTree;

import java.lang.reflect.Field;
import java.util.List;

public final class CloudExtender {

    public static void unregister(CommandManager commandManager, Command command, String commandName) throws Exception {
        if(command == null) {
            return;
        }
        unregisterCommandsField(commandManager, command);
        unregisterTree(commandManager, commandName);
    }

    private static void unregisterTree(CommandManager commandManager, String commandName) throws Exception {
        CommandTree commandTree = commandManager.getCommandTree();
        CommandTree.Node commandNode = commandTree.getNamedNode(commandName);
        Field internalTreeField = commandTree.getClass().getDeclaredField("internalTree");
        internalTreeField.setAccessible(true);
        CommandTree.Node internalTree = (CommandTree.Node) internalTreeField.get(commandTree);
        Field childrenField = CommandTree.Node.class.getDeclaredField("children");
        childrenField.setAccessible(true);
        List<CommandTree.Node> children = (List<CommandTree.Node>) childrenField.get(internalTree);
        children.remove(commandNode);
    }

    private static void unregisterCommandsField(CommandManager commandManager, Command command) throws Exception {
        Field commandsField = CommandManager.class.getDeclaredField("commands");
        commandsField.setAccessible(true);
        List<Command> commands = (List<Command>) commandsField.get(commandManager);
        commands.remove(command);
    }

    private CloudExtender() {
    }
}
