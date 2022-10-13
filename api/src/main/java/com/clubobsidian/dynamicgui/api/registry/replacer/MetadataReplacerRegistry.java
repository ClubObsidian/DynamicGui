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

package com.clubobsidian.dynamicgui.api.registry.replacer;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.manager.gui.GuiManager;
import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.EventPriority;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

public abstract class MetadataReplacerRegistry implements ReplacerRegistry {

    public static final String METADATA_PREFIX = "%metadata_";

    @Inject
    private static MetadataReplacerRegistry instance;

    public static MetadataReplacerRegistry get() {
        return instance;
    }

}