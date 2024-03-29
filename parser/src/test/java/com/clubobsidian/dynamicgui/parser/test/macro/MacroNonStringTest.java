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

package com.clubobsidian.dynamicgui.parser.test.macro;

import com.clubobsidian.dynamicgui.api.parser.macro.MacroParser;
import com.clubobsidian.dynamicgui.api.parser.macro.MacroToken;
import com.clubobsidian.dynamicgui.parser.macro.SimpleMacroParser;
import com.clubobsidian.dynamicgui.parser.macro.SimpleMacroToken;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MacroNonStringTest {

    @Test
    public void testNonStringParseStringMacros() {
        File guiFolder = new File("test", "gui");
        File file = new File(guiFolder, "non-string-macro.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection macros = config.getConfigurationSection("macros");

        MacroToken token = new SimpleMacroToken(macros);
        List<MacroToken> tokens = new ArrayList<>();
        tokens.add(token);
        MacroParser parser = new SimpleMacroParser(tokens);
        String parsed = parser.parseStringMacros("%test-non-string%");
        assertEquals("1", parsed);
    }

    @Test
    public void testNonStringParseListMacros() {
        File guiFolder = new File("test", "gui");
        File file = new File(guiFolder, "non-string-macro.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection macros = config.getConfigurationSection("macros");
        System.out.println(macros.getKeys());

        MacroToken token = new SimpleMacroToken(macros);
        List<MacroToken> tokens = new ArrayList<>();
        tokens.add(token);
        MacroParser parser = new SimpleMacroParser(tokens);
        List<String> nonString = new ArrayList<>();
        nonString.add("%test-non-string%");
        nonString.add("%test-non-string%");
        List<String> parsed = parser.parseListMacros(nonString);
        assertEquals(2, parsed.size());
        assertEquals("1", parsed.get(0));
        assertEquals("1", parsed.get(1));
    }
}