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

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import com.clubobsidian.dynamicgui.core.Key;
import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.GuiManager;

public class GuiCommand {

    @CommandMethod("gui <guiName>")
    @CommandPermission(Key.GUI_BASE_PERMISSION)
    public void gui(GuiCommandSender sender, @Argument("guiName") String guiName) {
        PlayerWrapper<?> player = sender.getPlayer().orElse(null);
        if(player != null && Key.hasGuiPermission(player, guiName)) {
            GuiManager.get().openGui(player, guiName);
        }
    }
}