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
import com.clubobsidian.dynamicgui.core.gui.property.animation.AnimationHolder;
import com.clubobsidian.dynamicgui.core.registry.replacer.AnimationReplacerRegistry;
import com.clubobsidian.dynamicgui.api.replacer.AnimationReplacer;
import com.clubobsidian.dynamicgui.core.replacer.animation.MultiLineTestAnimationReplacer;
import com.clubobsidian.dynamicgui.core.replacer.animation.TestAnimationReplacer;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class DynamicGuiAnimationReplacerRegistry implements AnimationReplacerRegistry {

    private static DynamicGuiAnimationReplacerRegistry instance;

    private final Map<String, AnimationReplacer> replacers;

    private DynamicGuiAnimationReplacerRegistry() {
        this.replacers = new HashMap<>();
        this.addReplacer(new TestAnimationReplacer("%test-animation%"));
        this.addReplacer(new MultiLineTestAnimationReplacer("%test-multiline-animation%"));
    }

    public static DynamicGuiAnimationReplacerRegistry get() {
        if (instance == null) {
            instance = new DynamicGuiAnimationReplacerRegistry();
        }
        return instance;
    }

    public boolean addReplacer(AnimationReplacer replacer) {
        return this.replacers.put(replacer.getToReplace(), replacer) == null;
    }

    @Override
    public String replace(AnimationHolder holder, PlayerWrapper<?> playerWrapper, String text) {
        for (AnimationReplacer replacer : this.replacers.values()) {
            if (text.contains(replacer.getToReplace())) {
                text = StringUtils.replace(text, replacer.getToReplace(), replacer.replacement(playerWrapper, holder, text));
            }
        }
        return text;
    }
}