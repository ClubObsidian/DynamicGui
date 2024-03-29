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

package com.clubobsidian.dynamicgui.core.test.builder;

import com.clubobsidian.dynamicgui.api.parser.function.FunctionData;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionModifier;
import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionDataBuilderTest {

    @BeforeEach
    public void setup() {
        MockFactory factory = new MockFactory();
        factory.inject();
    }

    @Test
    public void nameTest() {
        String functionName = "test";
        FunctionData function = new FunctionData.Builder()
                .setName(functionName)
                .build();
        assertEquals(functionName, function.getName());
    }

    @Test
    public void dataTest() {
        String functionData = "data";
        FunctionData function = new FunctionData.Builder()
                .setData(functionData)
                .build();
        assertEquals(functionData, function.getData());
    }

    @Test
    public void modifierTest() {
        FunctionModifier functionModifier = FunctionModifier.NOT;
        FunctionData function = new FunctionData.Builder()
                .setModifier(functionModifier)
                .build();
        assertEquals(functionModifier, function.getModifier());
    }
}
