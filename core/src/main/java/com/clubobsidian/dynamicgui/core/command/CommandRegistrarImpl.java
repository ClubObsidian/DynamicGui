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

package com.clubobsidian.dynamicgui.core.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.meta.SimpleCommandMeta;
import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.command.CommandRegistrar;
import com.clubobsidian.dynamicgui.api.command.GuiCommandSender;
import com.clubobsidian.dynamicgui.api.command.RegisteredCommand;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.api.manager.entity.EntityManager;
import com.clubobsidian.dynamicgui.api.manager.gui.GuiManager;
import com.google.inject.Injector;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.*;

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
    public void registerCommand(@NotNull Class<? extends RegisteredCommand> command) {
        Objects.requireNonNull(command);
        this.commandParser.parse(this.injector.getInstance(command));
    }

    @Override
    public void registerGuiAliasCommand(@NotNull String guiName,
                                        @NotNull String alias,
                                        @NotNull Collection<CommandArgument> arguments) {
        Objects.requireNonNull(guiName);
        Objects.requireNonNull(alias);
        Objects.requireNonNull(arguments);
        this.unregisterCommand(alias);
        Command.@NonNull Builder<GuiCommandSender> builder = this.commandManager.commandBuilder(alias);
        builder = builder.handler(context -> {
            context.getSender().getPlayer()
                    .ifPresent(playerWrapper -> {
                        Map<String, String> metadata = new HashMap<>();
                        for (CommandArgument arg : arguments) {
                            String argName = arg.getName();
                            String metaKey = "command_" + argName;
                            context.getOptional(argName).ifPresent(value -> {
                                String metadataValue = EntityManager.get().isPlayer(value) ?
                                EntityManager.get().createPlayerWrapper(value).getName() :
                                String.valueOf(value);
                            metadata.put(metaKey, metadataValue);
                            });
                        }
                        GuiManager.get().openGui(playerWrapper, guiName, metadata);
                    });
        });
        for (CommandArgument arg : arguments) {
            builder = builder.argument(arg);
        }
        this.commandManager.command(builder.build());
        this.registeredAliases.add(alias);
        this.logger.info(
                "Registered the command '%s' for the gui %s",
                alias,
                guiName
        );
    }

    @Override
    public void unregisterCommand(@NotNull String alias) {
        Objects.requireNonNull(alias);
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
