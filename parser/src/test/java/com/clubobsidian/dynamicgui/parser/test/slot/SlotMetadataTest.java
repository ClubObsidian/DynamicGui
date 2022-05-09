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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SlotMetadataTest {

    @Test
    public void testSlotMetadata() {
        File slotFolder = new File("test", "slot");
        File metadataFile = new File(slotFolder, "metadata.yml");
        Configuration config = Configuration.load(metadataFile);
        ConfigurationSection slot = config.getConfigurationSection("0");
        SlotToken token = new SlotToken(0, slot);
        Map<String, String> metadata = token.getMetadata();
        assertEquals(1, metadata.size());
        assertEquals("metadata", metadata.get("some"));
    }

    @Test
    public void testSlotNoMetadata() {
        File slotFolder = new File("test", "slot");
        File metadataFile = new File(slotFolder, "metadata.yml");
        Configuration config = Configuration.load(metadataFile);
        ConfigurationSection slot = config.getConfigurationSection("1");
        SlotToken token = new SlotToken(1, slot);
        Map<String, String> metadata = token.getMetadata();
        assertEquals(0, metadata.size());
    }
}