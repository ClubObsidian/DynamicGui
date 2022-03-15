/*
 *    Copyright 2022 virustotalop and contributors.
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

package com.clubobsidian.dynamicgui.parser.test.slot;

import com.clubobsidian.dynamicgui.parser.slot.SlotToken;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidUpdateIntervalTest {

    @Test
    public void lessThanZeroupdateIntervalTest() {
        File slotFolder = new File("test", "slot");
        File file = new File(slotFolder, "invalid-update-interval.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection section = config.getConfigurationSection("0");
        SlotToken token = new SlotToken(0, section);
        int interval = token.getUpdateInterval();
        assertEquals(0, interval);
    }

    @Test
    public void invalidIntervalTest() {
        File slotFolder = new File("test", "slot");
        File file = new File(slotFolder, "invalid-update-interval.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection section = config.getConfigurationSection("1");
        SlotToken token = new SlotToken(1, section);
        int interval = token.getUpdateInterval();
        assertEquals(0, interval);
    }
}