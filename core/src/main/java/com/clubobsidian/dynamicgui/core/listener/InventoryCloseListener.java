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

package com.clubobsidian.dynamicgui.core.listener;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.event.inventory.InventoryCloseEvent;
import com.clubobsidian.dynamicgui.core.event.player.PlayerKickEvent;
import com.clubobsidian.dynamicgui.core.event.player.PlayerQuitEvent;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.GuiManager;
import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.EventPriority;

public class InventoryCloseListener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void inventoryClose(final InventoryCloseEvent e) {
        this.handleInventoryClose(e.getPlayerWrapper());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(final PlayerQuitEvent e) {
        this.handleInventoryClose(e.getPlayerWrapper());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKick(final PlayerKickEvent e) {
        this.handleInventoryClose(e.getPlayerWrapper());
    }

    private void handleInventoryClose(PlayerWrapper<?> playerWrapper) {
        if (GuiManager.get().hasGuiCurrently(playerWrapper)) {
            GuiManager.get().cleanupPlayerGui(playerWrapper);
        }
    }
}