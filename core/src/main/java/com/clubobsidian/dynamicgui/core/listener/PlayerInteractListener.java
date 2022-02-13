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
package com.clubobsidian.dynamicgui.core.listener;

import com.clubobsidian.dynamicgui.core.event.block.PlayerInteractEvent;
import com.clubobsidian.dynamicgui.core.event.player.PlayerAction;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.core.world.LocationWrapper;
import com.clubobsidian.trident.EventHandler;

public class PlayerInteractListener {

    @EventHandler
    public void playerInteract(final PlayerInteractEvent e) {
        if(e.getAction().isBlockClick(e.getAction())) {
            if(GuiManager.get().hasGuiCurrently(e.getPlayerWrapper())) {
                return;
            }

            for(Gui gui : GuiManager.get().getGuis()) {
                if(gui.getLocations() != null) {
                    for(LocationWrapper<?> guiLocation : gui.getLocations()) {
                        if(e.getLocationWrapper().equals(guiLocation)) {
                            GuiManager.get().openGui(e.getPlayerWrapper(), gui);
                            e.setCancelled(true);
                            break;
                        }
                    }
                }
            }
        }
    }
}