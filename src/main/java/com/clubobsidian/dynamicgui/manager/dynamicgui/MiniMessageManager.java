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

package com.clubobsidian.dynamicgui.manager.dynamicgui;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.event.DynamicGuiReloadEvent;
import com.clubobsidian.trident.EventHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.util.HashMap;
import java.util.Map;

public class MiniMessageManager {

    public static MiniMessageManager instance;

    public static MiniMessageManager get() {
        if(instance == null) {
            instance = new MiniMessageManager();
        }
        return instance;
    }

    private final Map<String, String> json;
    private final MiniMessage miniMessage;
    private final GsonComponentSerializer serializer;

    private MiniMessageManager() {
        this.json = new HashMap<>();
        this.miniMessage = MiniMessage
                .builder()
                .markdown()
                .build();
        this.serializer = GsonComponentSerializer.builder().build();
        DynamicGui.get().getEventBus().registerEvents(this);
    }

    public String toJson(String data) {
        String cached = this.json.get(data);
        if(cached == null) {
            Component component = this.miniMessage.deserialize(data);
            cached = this.serializer.serialize(component);
            this.json.put(data, cached);
        }
        return cached;
    }

    @EventHandler
    public void onReload(DynamicGuiReloadEvent event) {
        this.json.clear();
    }
}