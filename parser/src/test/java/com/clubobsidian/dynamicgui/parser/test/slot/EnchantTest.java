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

import com.clubobsidian.dynamicgui.api.parser.slot.SlotToken;
import com.clubobsidian.dynamicgui.parser.slot.SimpleSlotToken;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnchantTest {

    @Test
    public void enchantTest() {
        File slotFolder = new File("test", "slot");
        File file = new File(slotFolder, "enchants.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection section = config.getConfigurationSection("0");
        SlotToken token = new SimpleSlotToken(0, section);
        List<String> enchants = token.getEnchants();
        assertEquals(3, enchants.size());
    }
}