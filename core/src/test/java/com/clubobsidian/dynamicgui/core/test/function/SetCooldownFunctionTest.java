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

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.core.function.cooldown.SetCooldownFunction;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetCooldownFunctionTest extends FactoryTest {

    @Test
    public void nullDataTest() throws Exception {
        Function function = new SetCooldownFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void noCommaTest() throws Exception {
        Function function = new SetCooldownFunction();
        function.setData("a");
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void invalidNumberTest() throws Exception {
        Function function = new SetCooldownFunction();
        function.setData("a,a");
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void lengthNotTwoTest() throws Exception {
        Function function = new SetCooldownFunction();
        function.setData("a,a,a");
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void onCooldownTest() throws Exception {
        this.getFactory().inject();
        PlayerWrapper<?> player = this.getFactory().createPlayer();
        String cooldownName = UUID.randomUUID().toString();
        String data = cooldownName + ",1000";
        Function function = new SetCooldownFunction();
        function.setData(data);
        assertTrue(function.function(player));
    }
}