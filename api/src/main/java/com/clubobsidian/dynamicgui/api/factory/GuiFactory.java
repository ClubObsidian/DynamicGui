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

package com.clubobsidian.dynamicgui.api.factory;

import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.gui.GuiBuildType;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.api.world.LocationWrapper;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Map;

@ApiStatus.Internal
public interface GuiFactory {

    Gui create(String name, String type, String title, int rows, Boolean close,
               GuiBuildType guiBuildType, Map<String, List<Integer>> npcIds, List<Slot> slots,
               List<LocationWrapper<?>> locations, FunctionTree functions, Map<String, String> metadata,
               boolean isStatic, boolean legacyIndexing);

}