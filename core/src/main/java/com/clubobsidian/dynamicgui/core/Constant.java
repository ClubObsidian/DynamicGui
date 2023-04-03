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

package com.clubobsidian.dynamicgui.core;

import java.util.function.Function;

public final class Constant {

    public static final String GUI_BASE_PERMISSION = "dynamicgui.command.gui";
    public static final Function<String, String> GUI_COMMAND_PERMISSION = (gui) -> GUI_BASE_PERMISSION + "." + gui;
    public static final String ADMIN_BASE_PERMISSION = "dynamicgui.command.admin";
    public static final String RELOAD_COMMAND_PERMISSION = ADMIN_BASE_PERMISSION + ".reload";
    public static final String CLOSE_COMMAND_PERMISSION = ADMIN_BASE_PERMISSION + ".close";
    public static final String LIST_COMMAND_PERMISSION = ADMIN_BASE_PERMISSION + ".list";

    private Constant() {
    }
}
