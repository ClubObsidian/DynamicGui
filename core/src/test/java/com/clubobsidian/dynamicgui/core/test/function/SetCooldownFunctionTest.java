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
import com.clubobsidian.dynamicgui.core.function.impl.cooldown.SetCooldownFunction;
import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetCooldownFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void nullDataTest() {
        Function function = new SetCooldownFunction();
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void noCommaTest() {
        Function function = new SetCooldownFunction();
        function.setData("a");
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void invalidNumberTest() {
        Function function = new SetCooldownFunction();
        function.setData("a,a");
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void lengthNotTwoTest() {
        Function function = new SetCooldownFunction();
        function.setData("a,a,a");
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void onCooldownTest() {
        this.factory.inject();
        PlayerWrapper<?> player = this.factory.createPlayer();
        String cooldownName = UUID.randomUUID().toString();
        String data = cooldownName + ",1000";
        Function function = new SetCooldownFunction();
        function.setData(data);
        assertTrue(function.function(player));
    }
}