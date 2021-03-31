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
package com.clubobsidian.dynamicgui.function.impl.cooldown;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.manager.dynamicgui.cooldown.CooldownManager;

public class IsOnCooldownFunction extends Function {


    /**
     *
     */
    private static final long serialVersionUID = 2369277150280303056L;

    public IsOnCooldownFunction(String name) {
        super(name);
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        if (this.getData() == null) {
            return false;
        }

        return CooldownManager.get().isOnCooldown(playerWrapper, this.getData());
    }
}