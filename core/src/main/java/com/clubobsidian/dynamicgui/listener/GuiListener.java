/*
 *    Copyright 2021 Club Obsidian and contributors.
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
package com.clubobsidian.dynamicgui.listener;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.inventory.GuiLoadEvent;
import com.clubobsidian.dynamicgui.event.inventory.InventoryCloseEvent;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.function.FunctionUtil;
import com.clubobsidian.trident.EventHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GuiListener {

    private final Set<UUID> users;

    public GuiListener() {
        this.users = new HashSet<>();
    }

    @EventHandler
    public void onGuiOpen(GuiLoadEvent event) {
        PlayerWrapper<?> wrapper = event.getPlayerWrapper();
        Gui gui = GuiManager.get().getCurrentGui(wrapper);
        PlayerWrapper<?> playerWrapper = event.getPlayerWrapper();
        boolean open = (gui != null);
        if(open) {
            UUID uuid = wrapper.getUniqueId();
            this.users.add(uuid);
            FunctionUtil.tryFunctions(gui, FunctionType.SWITCH_MENU, playerWrapper);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        PlayerWrapper<?> playerWrapper = event.getPlayerWrapper();
        UUID uuid = playerWrapper.getUniqueId();
        if(!this.users.remove(uuid)) {
            Gui gui = GuiManager.get().getCurrentGui(playerWrapper);
            if(gui != null) {
                FunctionUtil.tryFunctions(gui, FunctionType.EXIT_MENU, playerWrapper);
            }
        }
    }
}