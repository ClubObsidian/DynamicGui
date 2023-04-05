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

package com.clubobsidian.dynamicgui.core.listener;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.manager.FunctionManager;
import com.clubobsidian.dynamicgui.api.manager.gui.GuiManager;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.core.event.inventory.GuiSwitchEvent;
import com.clubobsidian.dynamicgui.core.event.inventory.InventoryCloseEvent;
import com.clubobsidian.trident.EventHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GuiListener {

    private final Set<UUID> users = new HashSet<>();

    @EventHandler
    public void onGuiSwitch(GuiSwitchEvent event) {
        Gui gui = event.getSwitchFrom();
        PlayerWrapper<?> playerWrapper = event.getPlayerWrapper();
        FunctionManager.get().tryFunctions(gui, FunctionType.SWITCH_MENU, playerWrapper);
        this.users.add(playerWrapper.getUniqueId());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        PlayerWrapper<?> playerWrapper = event.getPlayerWrapper();
        UUID uuid = playerWrapper.getUniqueId();
        if (!this.users.remove(uuid)) {
            Gui gui = GuiManager.get().getPlayerGui(playerWrapper);
            if (gui != null) {
                FunctionManager.get().tryFunctions(gui, FunctionType.EXIT_MENU, playerWrapper);
            }
        }
    }
}