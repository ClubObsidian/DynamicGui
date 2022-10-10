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

package com.clubobsidian.dynamicgui.core.manager;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.manager.MiniMessageManager;
import com.clubobsidian.dynamicgui.core.event.plugin.DynamicGuiReloadEvent;
import com.clubobsidian.trident.EventBus;
import com.clubobsidian.trident.EventHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class MiniMessageManagerImpl extends MiniMessageManager {

    private final Map<String, String> json = new HashMap<>();
    private final MiniMessage miniSerializer = MiniMessage.builder().build();
    private final GsonComponentSerializer gsonSerializer = GsonComponentSerializer.builder().build();

    @Inject
    private MiniMessageManagerImpl(EventBus eventBus) {
        eventBus.registerEvents(this);
    }

    @Override
    public String toJson(String data) {
        String cached = this.json.get(data);
        if (cached == null) {
            Component component = this.miniSerializer.deserialize(data);
            cached = this.gsonSerializer.serialize(component);
            this.json.put(data, cached);
        }
        return cached;
    }

    @EventHandler
    public void onReload(DynamicGuiReloadEvent event) {
        this.json.clear();
    }
}