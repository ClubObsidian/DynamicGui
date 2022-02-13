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

package com.clubobsidian.dynamicgui.parser.test;

import com.clubobsidian.dynamicgui.parser.function.FunctionModifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionModifierTest {

    @Test
    public void testHasNotModifier() {
        FunctionModifier modifier = FunctionModifier.findModifier("!aaa");
        assertEquals(modifier, FunctionModifier.NOT);
    }


    @Test
    public void testNoModifier() {
        FunctionModifier modifier = FunctionModifier.findModifier("aaa");
        assertEquals(modifier, FunctionModifier.NONE);
    }

}
