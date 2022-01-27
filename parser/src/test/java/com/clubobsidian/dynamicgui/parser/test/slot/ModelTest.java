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

package com.clubobsidian.dynamicgui.parser.test.slot;

import com.clubobsidian.dynamicgui.parser.slot.SlotToken;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ModelTest {

    @Test
    public void testModelProvider() {
        File slotFolder = new File("test", "slot");
        File file = new File(slotFolder, "model.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection section = config.getConfigurationSection("0");
        SlotToken token = new SlotToken(0, section);
        assertEquals("vanilla", token.getModelProvider());
    }

    @Test
    public void testModelData() {
        File slotFolder = new File("test", "slot");
        File file = new File(slotFolder, "model.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection section = config.getConfigurationSection("0");
        SlotToken token = new SlotToken(0, section);
        assertEquals("1", token.getModelData());
    }

    @Test
    public void testNonExistentModelProvider() {
        File slotFolder = new File("test", "slot");
        File file = new File(slotFolder, "model.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection section = config.getConfigurationSection("1");
        SlotToken token = new SlotToken(1, section);
        assertNull(token.getModelProvider());
    }

    @Test
    public void testNonExistentModelData() {
        File slotFolder = new File("test", "slot");
        File file = new File(slotFolder, "model.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection section = config.getConfigurationSection("1");
        SlotToken token = new SlotToken(1, section);
        assertNull(token.getModelData());
    }
}