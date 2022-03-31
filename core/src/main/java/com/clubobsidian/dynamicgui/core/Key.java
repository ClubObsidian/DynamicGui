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

package com.clubobsidian.dynamicgui.core;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;

public final class Key {

    public static final String GUI_BASE_PERMISSION = "dynamicgui.command.gui";
    public static final String DYNAMIC_GUI_COMMAND_BASE_PERMISSION = "dynamicgui.command.admin";
    public static final String DYNAMIC_GUI_COMMAND_RELOAD_PERMISSION = DYNAMIC_GUI_COMMAND_BASE_PERMISSION + ".reload";
    public static final String DYNAMIC_GUI_COMMAND_CLOSE_PERMISSION = DYNAMIC_GUI_COMMAND_BASE_PERMISSION + ".close";

    public static boolean hasGuiPermission(PlayerWrapper<?> player, String guiName) {
        return player.hasPermission(GUI_BASE_PERMISSION + "." + guiName);
    }

    private Key() {
    }
}
