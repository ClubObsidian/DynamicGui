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
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.meta.SimpleCommandMeta;
import com.clubobsidian.dynamicgui.api.command.CommandRegistrar;
import com.clubobsidian.dynamicgui.api.command.GuiCommandSender;
import com.clubobsidian.dynamicgui.api.command.RegisteredCommand;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.manager.GuiManager;
import com.clubobsidian.dynamicgui.core.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.SimpleGuiManager;
import com.google.inject.Injector;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CommandRegistrarImpl implements CommandRegistrar {

    private final CommandManager<GuiCommandSender> commandManager;
    private final LoggerWrapper<?> logger;
    private final Injector injector;
    private final AnnotationParser<GuiCommandSender> commandParser;

    private final List<String> registeredAliases = new ArrayList<>();

    @Inject
    private CommandRegistrarImpl(CommandManager<GuiCommandSender> commandManager,
                                 LoggerWrapper<?> logger,
                                 Injector injector) {
        this.commandManager = commandManager;
        this.logger = logger;
        this.injector = injector;
        this.commandParser = new AnnotationParser<>(this.commandManager,
                GuiCommandSender.class,
                parserParameters -> SimpleCommandMeta.empty());
        this.commandManager.setSetting(CommandManager.ManagerSettings.ALLOW_UNSAFE_REGISTRATION, true);
        this.commandManager.setSetting(CommandManager.ManagerSettings.OVERRIDE_EXISTING_COMMANDS, true);
    }

    @Override
    public void registerCommand(Class<? extends RegisteredCommand> command) {
        this.commandParser.parse(this.injector.getInstance(command));
    }

    @Override
    public void registerGuiCommand(String guiName, String alias) {
        this.unregisterCommand(alias);
        Command<GuiCommandSender> command = this.commandManager.commandBuilder(alias)
                .handler(context -> {
                    PlayerWrapper<?> playerWrapper = context.getSender().getPlayer().get();
                    if(playerWrapper != null) {
                        GuiManager.get().openGui(playerWrapper, guiName);
                    }
                }).build();
        this.commandManager.command(command);
        this.registeredAliases.add(alias);
        this.logger.info(String.format("Registered the command \"%s\" for the gui %s",
                alias,
                guiName
        ));
    }

    @Override
    public void unregisterCommand(String alias) {
        try {
            this.commandManager.deleteRootCommand(alias);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.registeredAliases.remove(alias);
    }

    @Override
    public void unregisterGuiAliases() {
        for (int i = 0; i < this.registeredAliases.size(); i++) {
            String cmd = this.registeredAliases.get(i);
            this.unregisterCommand(cmd);
            i--;
        }
    }
}
