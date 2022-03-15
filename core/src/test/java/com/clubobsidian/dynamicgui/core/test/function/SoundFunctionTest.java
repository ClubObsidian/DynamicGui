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

import com.clubobsidian.dynamicgui.core.effect.SoundWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.function.impl.SoundFunction;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SoundFunctionTest extends FactoryTest {

    @Test
    public void nullTest() {
        Function function = new SoundFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void soundTest() {
        String type = SoundWrapper.TEST_SOUND_STRING;
        MockPlayerWrapper player = this.getFactory().createPlayer();
        Slot slot = this.getFactory().createSlot(player);
        Function function = new SoundFunction();
        function.setOwner(slot);
        function.setData(type);
        assertTrue(function.function(player));
        List<SoundWrapper.SoundData> sounds = player.getSounds();
        assertTrue(sounds.size() == 1);
        assertEquals(new SoundWrapper(SoundWrapper.TEST_SOUND_STRING).getData(), sounds.get(0));
    }
}
