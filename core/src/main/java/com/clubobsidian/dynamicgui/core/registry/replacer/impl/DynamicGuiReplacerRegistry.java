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

package com.clubobsidian.dynamicgui.core.registry.replacer.impl;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.replacer.Replacer;
import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.event.plugin.DynamicGuiReloadEvent;
import com.clubobsidian.dynamicgui.core.registry.replacer.ReplacerRegistry;
import com.clubobsidian.dynamicgui.core.replacer.GlobalPlayerCountReplacer;
import com.clubobsidian.dynamicgui.core.replacer.OnlinePlayersReplacer;
import com.clubobsidian.dynamicgui.core.replacer.PlayerLevelReplacer;
import com.clubobsidian.dynamicgui.core.replacer.PlayerReplacer;
import com.clubobsidian.dynamicgui.core.replacer.PreviousGuiReplacer;
import com.clubobsidian.dynamicgui.core.replacer.SkinTextureReplacer;
import com.clubobsidian.dynamicgui.core.replacer.UUIDReplacer;
import com.clubobsidian.trident.EventHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicGuiReplacerRegistry implements ReplacerRegistry {

    private static DynamicGuiReplacerRegistry instance;

    private final Map<String, Replacer> replacers;
    private final Map<String, List<Replacer>> cachedReplacers;

    private DynamicGuiReplacerRegistry() {
        this.replacers = new HashMap<>();
        this.cachedReplacers = new HashMap<>();
        this.addReplacer(new PlayerReplacer("%player%"));
        this.addReplacer(new OnlinePlayersReplacer("%online-players%"));
        this.addReplacer(new GlobalPlayerCountReplacer("%global-playercount%"));
        this.addReplacer(new UUIDReplacer("%uuid%"));
        this.addReplacer(new PlayerLevelReplacer("%player-level%"));
        this.addReplacer(new PreviousGuiReplacer("%previous-gui-name%"));
        this.addReplacer(new SkinTextureReplacer("%skin_texture%"));
        DynamicGui.get().getEventBus().registerEvents(this);
    }

    @Override
    public String replace(final PlayerWrapper<?> playerWrapper, final String text) {
        String newText = text;
        List<Replacer> cachedReplacerList = this.cachedReplacers.get(text);
        if (cachedReplacerList != null) {
            for (Replacer replacer : cachedReplacerList) {
                newText = StringUtils.replace(newText, replacer.getToReplace(), replacer.replacement(newText, playerWrapper));
            }
        } else {
            cachedReplacerList = new ArrayList<>();
            for (Replacer replacer : this.replacers.values()) {
                if (newText.contains(replacer.getToReplace())) {
                    newText = StringUtils.replace(newText, replacer.getToReplace(), replacer.replacement(newText, playerWrapper));
                    cachedReplacerList.add(replacer);
                }
            }

            this.cachedReplacers.put(text, cachedReplacerList);
        }


        return newText;
    }

    public static DynamicGuiReplacerRegistry get() {
        if (instance == null) {
            instance = new DynamicGuiReplacerRegistry();
        }
        return instance;
    }

    public boolean addReplacer(Replacer replacer) {
        boolean put = this.replacers.put(replacer.getToReplace(), replacer) == null;
        this.cachedReplacers.clear();
        return put;
    }

    @EventHandler
    public void onReload(DynamicGuiReloadEvent event) {
        this.cachedReplacers.clear();
    }
}