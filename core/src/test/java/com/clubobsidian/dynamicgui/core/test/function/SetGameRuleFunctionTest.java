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

package com.clubobsidian.dynamicgui.core.test.function;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.function.impl.SetGameRuleFunction;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import com.clubobsidian.dynamicgui.core.test.mock.world.MockWorldWrapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetGameRuleFunctionTest extends FactoryTest {

    @Test
    public void nullTest() {
        Function function = new SetGameRuleFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void dataDoesNotHaveCommaTest() {
        Function function = new SetGameRuleFunction();
        function.setData("a");
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void sizeNotThreeTest() {
        Function function = new SetGameRuleFunction();
        function.setData("a,a");
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void worldNullTest() {
        this.getFactory().inject();
        Function function = new SetGameRuleFunction();
        function.setData("test,key,value");
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void validTest() {
        MockWorldWrapper world = this.getFactory().inject().createWorld("test");
        this.getFactory().getPlatform().addWorld(world);
        Function function = new SetGameRuleFunction();
        function.setData("test,key,value");
        assertTrue(function.function(this.getFactory().createPlayer()));
        assertEquals("value", DynamicGui.get().getPlatform().getWorld("test").getGameRule("key"));
    }
}