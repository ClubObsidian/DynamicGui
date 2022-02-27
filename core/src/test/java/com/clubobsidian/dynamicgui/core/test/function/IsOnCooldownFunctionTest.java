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

package com.clubobsidian.dynamicgui.core.test.function;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.function.impl.cooldown.IsOnCooldownFunction;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.cooldown.CooldownManager;
import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsOnCooldownFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void nullDataTest() {
        Function function = new IsOnCooldownFunction();
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void onCooldownTest() {
        this.factory.inject();
        PlayerWrapper<?> player = this.factory.createPlayer();
        String cooldownName = UUID.randomUUID().toString();
        CooldownManager.get().createCooldown(player, cooldownName, 1000);
        Function function = new IsOnCooldownFunction();
        function.setData(cooldownName);
        assertTrue(function.function(player));
    }
}
