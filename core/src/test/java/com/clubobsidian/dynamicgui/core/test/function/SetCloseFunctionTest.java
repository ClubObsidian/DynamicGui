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
import com.clubobsidian.dynamicgui.core.function.impl.SetCloseFunction;
import com.clubobsidian.dynamicgui.core.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.core.gui.property.CloseableComponent;
import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.core.test.mock.gui.MockCloseableFunctionOwner;
import com.clubobsidian.dynamicgui.core.test.mock.gui.MockNonCloseableFunctionOwner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetCloseFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void nullTest() {
        Function function = new SetCloseFunction();
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void nonCloseableComponentTest() {
        Function function = new SetCloseFunction();
        function.setOwner(new MockNonCloseableFunctionOwner());
        function.setData("true");
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void invalidDataTest() {
        Function function = new SetCloseFunction();
        CloseableComponent component = new MockCloseableFunctionOwner();
        function.setOwner((FunctionOwner) component);
        function.setData("a");
        assertTrue(function.function(this.factory.createPlayer()));
        assertEquals(false, component.getClose());
    }

    @Test
    public void validDataTest() {
        Function function = new SetCloseFunction();
        CloseableComponent component = new MockCloseableFunctionOwner();
        function.setOwner((FunctionOwner) component);
        function.setData("true");
        assertTrue(function.function(this.factory.createPlayer()));
        assertEquals(true, component.getClose());
    }
}