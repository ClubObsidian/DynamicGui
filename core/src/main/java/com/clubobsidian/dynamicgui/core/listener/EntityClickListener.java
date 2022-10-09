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

import com.clubobsidian.dynamicgui.api.entity.EntityWrapper;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.manager.gui.GuiManager;
import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.event.inventory.PlayerInteractEntityEvent;
import com.clubobsidian.dynamicgui.api.registry.npc.NPC;
import com.clubobsidian.dynamicgui.api.registry.npc.NPCRegistry;
import com.clubobsidian.trident.EventHandler;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class EntityClickListener {

    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent e) {
        if (GuiManager.get().hasGuiCurrently(e.getPlayerWrapper())) {
            return;
        }

        EntityWrapper<?> entityWrapper = e.getEntityWrapper();
        List<NPCRegistry> registries = DynamicGui.get().getPlugin().getNPCRegistries();
        for (NPCRegistry registry : registries) {
            for (Gui gui : GuiManager.get().getGuis()) {
                Iterator<Entry<String, List<Integer>>> it = gui.getNpcIds().entrySet().iterator();
                while (it.hasNext()) {
                    Entry<String, List<Integer>> next = it.next();
                    String registryName = next.getKey();
                    List<Integer> ids = next.getValue();

                    if (registryName.equalsIgnoreCase(registry.getName())) {
                        NPC npc = registry.getNPC(entityWrapper);
                        if (npc != null) {
                            if (ids.contains(npc.getMeta().getId())) {
                                GuiManager.get().openGui(e.getPlayerWrapper(), gui);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}