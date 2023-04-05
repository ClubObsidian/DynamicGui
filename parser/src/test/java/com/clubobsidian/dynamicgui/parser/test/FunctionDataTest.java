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

package com.clubobsidian.dynamicgui.parser.test;

import com.clubobsidian.dynamicgui.api.parser.function.FunctionData;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionModifier;
import com.clubobsidian.dynamicgui.parser.function.SimpleFunctionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionDataTest {

    private static final String NAME = "name";
    private static final String DATA = "data";
    private static final FunctionModifier MODIFIER = FunctionModifier.NONE;

    private FunctionData data;


    @BeforeEach
    public void setup() {
        this.data = new SimpleFunctionData(NAME, DATA, MODIFIER);
    }

    @Test
    public void testName() {
        Assertions.assertEquals(this.data.getName(), NAME);
    }

    @Test
    public void testData() {
        Assertions.assertEquals(this.data.getData(), DATA);
    }

    @Test
    public void testModifier() {
        Assertions.assertEquals(this.data.getModifier(), MODIFIER);
    }
}