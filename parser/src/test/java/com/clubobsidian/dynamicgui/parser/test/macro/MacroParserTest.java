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

public class MacroParserTest {

    @Test
    public void testMultilineParsing() {
        File test = new File("test.yml");
        Configuration config = Configuration.load(test);

        ConfigurationSection first = config.getConfigurationSection("2");
        ConfigurationSection macros = first.getConfigurationSection("macros");

        List<String> lore = first.getStringList("lore");

        MacroToken token = new SimpleMacroToken(macros);

        List<MacroToken> tokens = new ArrayList<>();
        tokens.add(token);

        MacroParser parser = new SimpleMacroParser(tokens);

        List<String> newLore = parser.parseListMacros(lore);

        System.out.println("newLore:" + newLore.toString());

        assertEquals(8, newLore.size());
        assertEquals("This is some text", newLore.get(0));
        assertEquals("Replace some text", newLore.get(1));
        assertEquals("and some other text test", newLore.get(2));
        assertEquals("with some other text", newLore.get(3));
        assertEquals("Replace some text", newLore.get(4));
        assertEquals("and some other text", newLore.get(5));
        assertEquals("with some other text", newLore.get(6));
        assertEquals("not-a-macro", newLore.get(7));
    }

    @Test
    public void testSmallMultiLine() {
        File test = new File("test.yml");
        Configuration config = Configuration.load(test);

        ConfigurationSection first = config.getConfigurationSection("3");
        ConfigurationSection macros = first.getConfigurationSection("macros");

        List<String> lore = first.getStringList("lore");

        MacroToken token = new SimpleMacroToken(macros);

        List<MacroToken> tokens = new ArrayList<>();
        tokens.add(token);

        MacroParser parser = new SimpleMacroParser(tokens);

        List<String> newLore = parser.parseListMacros(lore);

        System.out.println(newLore.toString());

        assertEquals(2, newLore.size());
        assertEquals("Replace some text", newLore.get(0));
        assertEquals("test", newLore.get(1));
    }

    @Test
    public void testOutOfIndexSmallMultiLine() {
        File test = new File("test.yml");
        Configuration config = Configuration.load(test);

        ConfigurationSection first = config.getConfigurationSection("4");
        ConfigurationSection macros = first.getConfigurationSection("macros");

        List<String> lore = first.getStringList("lore");

        MacroToken token = new SimpleMacroToken(macros);

        List<MacroToken> tokens = new ArrayList<>();
        tokens.add(token);

        MacroParser parser = new SimpleMacroParser(tokens);

        List<String> newLore = parser.parseListMacros(lore);

        System.out.println("New lore: " + newLore + " Size: " + newLore.size());

        assertEquals("Replace some text", newLore.get(0));

    }

    @Test
    public void testSingleLineParsing() {
        File test = new File("test.yml");
        Configuration config = Configuration.load(test);

        ConfigurationSection first = config.getConfigurationSection("2");
        ConfigurationSection macros = first.getConfigurationSection("macros");

        String name = first.getString("name");

        MacroToken token = new SimpleMacroToken(macros);

        List<MacroToken> tokens = new ArrayList<>();
        tokens.add(token);

        MacroParser parser = new SimpleMacroParser(tokens);

        String newName = parser.parseStringMacros(name);

        assertEquals("A name", newName);
    }

    @Test
    public void testMacroChaining() {
        File test = new File("test.yml");
        Configuration config = Configuration.load(test);

        ConfigurationSection fifth = config.getConfigurationSection("5");
        ConfigurationSection fithMacrosSection = fifth.getConfigurationSection("macros");
        ConfigurationSection guiMacrosSection = config.getConfigurationSection("macros");

        List<String> lore = fifth.getStringList("lore");

        MacroToken guiToken = new SimpleMacroToken(guiMacrosSection);
        MacroToken fifthToken = new SimpleMacroToken(fithMacrosSection);

        List<MacroToken> tokens = new ArrayList<>();
        tokens.add(guiToken);
        tokens.add(fifthToken);

        MacroParser parser = new SimpleMacroParser(tokens);

        List<String> parsedLore = parser.parseListMacros(lore);

        System.out.println("parsedLore: " + parsedLore.size());

        assertEquals(5, parsedLore.size());
        assertEquals("not-a-macro", parsedLore.get(0));
        assertEquals("Replace some text", parsedLore.get(1));
        assertEquals("and some other text test", parsedLore.get(2));
        assertEquals("with some other text", parsedLore.get(3));
        assertEquals("still-not-a-macro", parsedLore.get(4));
    }
}