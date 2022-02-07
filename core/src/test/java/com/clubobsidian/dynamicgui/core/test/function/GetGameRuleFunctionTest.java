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

import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.function.impl.GetGameRuleFunction;
import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.core.test.mock.plugin.MockPlatform;
import com.clubobsidian.dynamicgui.core.world.WorldWrapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetGameRuleFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void nullTest() {
        Function function = new GetGameRuleFunction();
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void noCommaTest() {
        Function function = new GetGameRuleFunction();
        function.setData("");
        assertFalse(function.function(this.factory.createPlayer()));
    }
    @Test
    public void lengthNotThreeTest() {
        Function function = new GetGameRuleFunction();
        function.setData("a,b");
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void worldNullTest() {
        this.factory.inject().getPlatform();
        Function function = new GetGameRuleFunction();
        function.setData("test,key,value");
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void hasGameRuleTest() {
        String worldName = "test";
        MockPlatform platform = this.factory.inject().getPlatform();
        WorldWrapper<?> world = this.factory.createWorld(worldName);
        platform.addWorld(world);
        world.setGameRule("key", "value");
        Function function = new GetGameRuleFunction();
        function.setData("test,key,value");
        assertTrue(function.function(this.factory.createPlayer()));
    }
}