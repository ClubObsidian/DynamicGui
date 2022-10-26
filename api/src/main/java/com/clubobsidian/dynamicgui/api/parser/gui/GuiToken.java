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

package com.clubobsidian.dynamicgui.api.parser.gui;

import com.clubobsidian.dynamicgui.api.gui.GuiMode;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.api.parser.macro.MacroParser;
import com.clubobsidian.dynamicgui.api.parser.slot.SlotToken;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GuiToken extends Serializable {

    default String parseType(String type) {
        if (type == null) {
            return "CHEST";
        }
        return type.toUpperCase();
    }

    String getTitle();

    String getType();

    int getRows();

    GuiMode getMode();

    Boolean isClosed();

    List<String> getAlias();

    List<String> getLocations();

    Map<String, List<Integer>> getNpcs();

    Map<Integer, SlotToken> getSlots();

    FunctionTree getFunctions();

    MacroParser getMacroParser();

    List<String> getLoadMacros();

    Map<String, String> getMetadata();

    boolean isStatic();
}