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

package com.clubobsidian.dynamicgui.parser.test.gui;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.clubobsidian.dynamicgui.parser.gui.GuiToken;
import com.clubobsidian.wrappy.Configuration;

public class LocationTest {

    @Test
    public void testAlias() {
        File slotFolder = new File("test", "gui");
        File file = new File(slotFolder, "location.yml");
        Configuration config = Configuration.load(file);
        GuiToken token = new GuiToken(config);
        String location = token.getLocations().get(0);
        assertTrue("Location is not '0,0,0,world'", location.equals("0,0,0,world"));
    }

}