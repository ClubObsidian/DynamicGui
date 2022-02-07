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
import com.clubobsidian.dynamicgui.function.impl.IsBedrockPlayerFunction;
import com.clubobsidian.dynamicgui.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.test.mock.entity.player.MockPlayerWrapper;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class IsBedrockPlayerFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void functionTest() {
        String name = "test";
        UUID uuid = new UUID(0, 1);
        MockPlayerWrapper player = this.factory.createPlayer(name, uuid);
        Function function = new IsBedrockPlayerFunction();
        assertTrue(function.function(player));
    }
}