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

package com.clubobsidian.dynamicgui.core.command.cloud.extender;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.CommandTree;
import com.clubobsidian.dynamicgui.core.command.cloud.CloudExtender;

import java.lang.reflect.Field;
import java.util.List;

public class NativeCloudExtender implements CloudExtender {

    public boolean unregister(CommandManager commandManager, Command command, String commandName) {
        if(command == null) {
            return false;
        }
        boolean commandsUnregistered;
        boolean treeUnregistered;
        try {
            commandsUnregistered = unregisterCommandsField(commandManager, command);
            treeUnregistered = unregisterTree(commandManager, commandName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        System.out.println(commandName + ": " + commandsUnregistered + " " + treeUnregistered);
        return commandsUnregistered || treeUnregistered;
    }

    private boolean unregisterTree(CommandManager commandManager, String commandName) throws Exception {
        CommandTree commandTree = commandManager.getCommandTree();
        CommandTree.Node commandNode = commandTree.getNamedNode(commandName);
        Field internalTreeField = commandTree.getClass().getDeclaredField("internalTree");
        internalTreeField.setAccessible(true);
        CommandTree.Node internalTree = (CommandTree.Node) internalTreeField.get(commandTree);
        Field childrenField = CommandTree.Node.class.getDeclaredField("children");
        childrenField.setAccessible(true);
        List<CommandTree.Node> children = (List<CommandTree.Node>) childrenField.get(internalTree);
        return children.remove(commandNode);
    }

    private boolean unregisterCommandsField(CommandManager commandManager, Command command) throws Exception {
        Field commandsField = CommandManager.class.getDeclaredField("commands");
        commandsField.setAccessible(true);
        List<Command> commands = (List<Command>) commandsField.get(commandManager);
        return commands.remove(command);
    }
}
