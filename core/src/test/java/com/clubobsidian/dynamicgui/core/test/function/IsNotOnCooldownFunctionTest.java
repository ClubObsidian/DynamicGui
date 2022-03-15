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
import com.clubobsidian.dynamicgui.core.function.impl.cooldown.IsNotOnCooldownFunction;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsNotOnCooldownFunctionTest extends FactoryTest {

    @Test
    public void nullDataTest() {
        Function function = new IsNotOnCooldownFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void notOnCooldownTest() {
        this.getFactory().inject();
        Function function = new IsNotOnCooldownFunction();
        function.setData(UUID.randomUUID().toString());
        assertTrue(function.function(this.getFactory().createPlayer()));
    }
}
