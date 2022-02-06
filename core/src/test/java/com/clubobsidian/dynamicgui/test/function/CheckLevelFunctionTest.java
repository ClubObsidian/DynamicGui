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

package com.clubobsidian.dynamicgui.test.function;

import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.function.impl.CheckLevelFunction;
import com.clubobsidian.dynamicgui.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.test.mock.MockPlayer;
import com.clubobsidian.dynamicgui.test.mock.MockPlayerWrapper;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CheckLevelFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void testNull() {
        MockPlayerWrapper wrapper = this.factory.createPlayer();
        Function function = new CheckLevelFunction();
        assertFalse(function.function(wrapper));
    }

    @Test
    public void testLevelGreater() {
        MockPlayerWrapper wrapper = this.factory.createPlayer();
        wrapper.setLevel(10);
        Function function = new CheckLevelFunction();
        function.setData("1");
        assertTrue(function.function(wrapper));
    }

    @Test
    public void testLevelEqual() {
        MockPlayerWrapper wrapper = this.factory.createPlayer();
        wrapper.setLevel(10);
        Function function = new CheckLevelFunction();
        function.setData("10");
        assertTrue(function.function(wrapper));
    }

    @Test
    public void testLevelLessThan() {
        MockPlayerWrapper wrapper = this.factory.createPlayer();
        wrapper.setLevel(1);
        Function function = new CheckLevelFunction();
        function.setData("10");
        assertFalse(function.function(wrapper));
    }

    @Test
    public void testInvalidFormat() {
        MockPlayerWrapper wrapper = this.factory.createPlayer();
        wrapper.setLevel(1);
        Function function = new CheckLevelFunction();
        function.setData("a");
        assertFalse(function.function(wrapper));
    }
}