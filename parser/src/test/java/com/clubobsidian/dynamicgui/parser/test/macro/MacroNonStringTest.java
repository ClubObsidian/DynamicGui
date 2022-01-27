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

package com.clubobsidian.dynamicgui.parser.test.macro;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.clubobsidian.dynamicgui.parser.macro.MacroParser;
import com.clubobsidian.dynamicgui.parser.macro.MacroToken;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;

public class MacroNonStringTest {

    @Test
    public void testNonStringParseStringMacros() {
        File guiFolder = new File("test", "gui");
        File file = new File(guiFolder, "non-string-macro.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection macros = config.getConfigurationSection("macros");

        MacroToken token = new MacroToken(macros);
        List<MacroToken> tokens = new ArrayList<>();
        tokens.add(token);
        MacroParser parser = new MacroParser(tokens);
        String parsed = parser.parseStringMacros("%test-non-string%");
        assertTrue(parsed.equals("1"));
    }

    @Test
    public void testNonStringParseListMacros() {
        File guiFolder = new File("test", "gui");
        File file = new File(guiFolder, "non-string-macro.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection macros = config.getConfigurationSection("macros");
        System.out.println(macros.getKeys());

        MacroToken token = new MacroToken(macros);
        List<MacroToken> tokens = new ArrayList<>();
        tokens.add(token);
        MacroParser parser = new MacroParser(tokens);
        List<String> nonString = new ArrayList<>();
        nonString.add("%test-non-string%");
        nonString.add("%test-non-string%");
        List<String> parsed = parser.parseListMacros(nonString);
        assertTrue(parsed.size() == 2);
        assertTrue(parsed.get(0).equals("1"));
        assertTrue(parsed.get(1).equals("1"));
    }
}