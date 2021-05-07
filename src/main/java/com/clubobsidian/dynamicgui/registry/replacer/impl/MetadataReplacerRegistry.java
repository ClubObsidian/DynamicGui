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
package com.clubobsidian.dynamicgui.registry.replacer.impl;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.inventory.GuiLoadEvent;
import com.clubobsidian.dynamicgui.event.inventory.GuiPreloadEvent;
import com.clubobsidian.dynamicgui.event.inventory.InventoryCloseEvent;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.registry.replacer.ReplacerRegistry;
import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.EventPriority;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MetadataReplacerRegistry implements ReplacerRegistry {

    private static MetadataReplacerRegistry instance;

    public static MetadataReplacerRegistry get() {
        if(instance == null) {
            instance = new MetadataReplacerRegistry();
        }

        return instance;
    }

    private static final String METADATA_PREFIX = "%metadata_";

    private final Map<UUID, Gui> cachedGuis;

    private MetadataReplacerRegistry() {
        this.cachedGuis = new HashMap<>();
        DynamicGui.get().getEventBus().registerEvents(this);
    }

    @Override
    public String replace(PlayerWrapper<?> playerWrapper, String text) {
        UUID uuid = playerWrapper.getUniqueId();
        Gui gui = this.cachedGuis.get(uuid);
        if(gui == null) {
            return text;
        }

        for(Map.Entry<String, String> entry : gui.getMetadata().entrySet()) {
            String metadataKey = entry.getKey();
            String metadataValue = entry.getValue();
            String metadataReplacer = METADATA_PREFIX + metadataKey + "%";
            if(text.contains(metadataReplacer)) {
                text = StringUtils.replace(text, metadataReplacer, metadataValue);
            }
        }

        return text;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGuiPreload(GuiPreloadEvent event) {
        UUID uuid = event.getPlayerWrapper().getUniqueId();
        Gui gui = event.gui();
        this.cachedGuis.put(uuid, gui);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGuiLoad(GuiLoadEvent event) {
        if(event.isCanceled()) {
            UUID uuid = event.getPlayerWrapper().getUniqueId();
            this.cachedGuis.remove(uuid);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCanceled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        UUID uuid = event.getPlayerWrapper().getUniqueId();
        this.cachedGuis.remove(uuid);
    }
}