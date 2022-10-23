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
import com.clubobsidian.dynamicgui.api.manager.cooldown.Cooldown;
import com.clubobsidian.dynamicgui.api.manager.cooldown.CooldownManager;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.util.Collection;
import java.util.UUID;

public abstract class CooldownReplacerRegistry implements ReplacerRegistry {

    public static final String COOLDOWN_PREFIX = "%cooldown_";

    @Inject
    private static CooldownReplacerRegistry instance;

    public static CooldownReplacerRegistry get() {
        return instance;
    }

    public abstract String replace(PlayerWrapper<?> playerWrapper, String text);

}