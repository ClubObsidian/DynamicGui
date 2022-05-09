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
import com.clubobsidian.dynamicgui.core.Constant;
import com.clubobsidian.dynamicgui.core.command.cloud.CloudExtender;

import javax.inject.Inject;
import javax.inject.Named;

public class CombinedCloudExtender implements CloudExtender {

    private final CloudExtender nativeExtender;
    private final CloudExtender platformExtender;

    @Inject
    private CombinedCloudExtender(@Named(Constant.NATIVE_ANNOTATION) CloudExtender nativeExtender,
                                  @Named(Constant.PLATFORM_ANNOTATION) CloudExtender platformExtender) {
        this.nativeExtender = nativeExtender;
        this.platformExtender = platformExtender;
    }

    @Override
    public boolean unregister(CommandManager commandManager, Command command, String commandName) {
        boolean nativeUnregistered = this.nativeExtender.unregister(commandManager, command, commandName);
        boolean platformUnregistered = this.platformExtender.unregister(commandManager, command, commandName);
        return nativeUnregistered || platformUnregistered;
    }
}
