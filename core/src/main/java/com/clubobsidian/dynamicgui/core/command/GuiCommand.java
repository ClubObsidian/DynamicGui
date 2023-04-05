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

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.context.CommandContext;
import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.command.GuiCommandSender;
import com.clubobsidian.dynamicgui.api.command.RegisteredCommand;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.manager.gui.GuiManager;
import com.clubobsidian.dynamicgui.core.Constant;

import java.util.ArrayList;
import java.util.List;

public class GuiCommand implements RegisteredCommand {

    @CommandMethod("gui <guiName>")
    @CommandPermission(Constant.GUI_BASE_PERMISSION)
    private void gui(GuiCommandSender sender, @Argument(value = "guiName", suggestions = "guiName") String guiName) {
        PlayerWrapper<?> player = sender.getPlayer().orElse(null);
        if (player != null) {
            boolean hasPermission = player.hasPermission(Constant.GUI_COMMAND_PERMISSION.apply(guiName));
            if (hasPermission) {
                GuiManager.get().openGui(player, guiName);
            } else {
                sender.sendMessage(DynamicGui.get().getConfig().getMessage().getNoGuiPermission());
            }
        }
    }

    @Suggestions("guiName")
    public List<String> guiNameSuggestion(CommandContext<GuiCommandSender> sender, String input) {
        List<String> guiNames = new ArrayList<>();
        for (Gui gui : GuiManager.get().getGuis()) {
            guiNames.add(gui.getName());
        }
        return guiNames;
    }
}