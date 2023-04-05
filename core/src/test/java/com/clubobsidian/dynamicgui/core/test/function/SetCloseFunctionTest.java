/*
 *    Copyright 2018-2023 virustotalop
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

import com.clubobsidian.dynamicgui.api.component.CloseableComponent;
import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.api.function.FunctionOwner;
import com.clubobsidian.dynamicgui.core.function.SetCloseFunction;
import com.clubobsidian.dynamicgui.core.test.mock.gui.MockCloseableFunctionOwner;
import com.clubobsidian.dynamicgui.core.test.mock.gui.MockNonCloseableFunctionOwner;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetCloseFunctionTest extends FactoryTest {

    @Test
    public void nullTest() throws Exception {
        Function function = new SetCloseFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void nonCloseableComponentTest() throws Exception {
        Function function = new SetCloseFunction();
        function.setOwner(new MockNonCloseableFunctionOwner());
        function.setData("true");
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void invalidDataTest() throws Exception {
        Function function = new SetCloseFunction();
        CloseableComponent component = new MockCloseableFunctionOwner();
        function.setOwner((FunctionOwner) component);
        function.setData("a");
        assertTrue(function.function(this.getFactory().createPlayer()));
        assertEquals(false, component.getClose());
    }

    @Test
    public void validDataTest() throws Exception {
        Function function = new SetCloseFunction();
        CloseableComponent component = new MockCloseableFunctionOwner();
        function.setOwner((FunctionOwner) component);
        function.setData("true");
        assertTrue(function.function(this.getFactory().createPlayer()));
        assertEquals(true, component.getClose());
    }
}