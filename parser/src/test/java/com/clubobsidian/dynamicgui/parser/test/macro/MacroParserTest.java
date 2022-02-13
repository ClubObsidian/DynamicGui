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

public class MacroParserTest {

    @Test
    public void testMultilineParsing() {
        File test = new File("test.yml");
        Configuration config = Configuration.load(test);

        ConfigurationSection first = config.getConfigurationSection("2");
        ConfigurationSection macros = first.getConfigurationSection("macros");

        List<String> lore = first.getStringList("lore");

        MacroToken token = new MacroToken(macros);

        List<MacroToken> tokens = new ArrayList<>();
        tokens.add(token);

        MacroParser parser = new MacroParser(tokens);

        List<String> newLore = parser.parseListMacros(lore);

        System.out.println("newLore:" + newLore.toString());

        assertTrue("New lore size is not 8", newLore.size() == 8);
        assertTrue("First line is not 'This is some gui text'", newLore.get(0).equals("This is some text"));
        assertTrue("Second line is not 'Replace some text'", newLore.get(1).equals("Replace some text"));
        assertTrue("Third line is not 'and some other text test'", newLore.get(2).equals("and some other text test"));
        assertTrue("Forth line is not 'with some other text'", newLore.get(3).equals("with some other text"));
        assertTrue("Fifth line is not 'Replace some text'", newLore.get(4).equals("Replace some text"));
        assertTrue("Sixth line is not 'and some other text'", newLore.get(5).equals("and some other text"));
        assertTrue("Seventh line is not 'with some other text'", newLore.get(6).equals("with some other text"));
        assertTrue("Eigth line is not 'not-a-macro'", newLore.get(7).equals("not-a-macro"));
    }

    @Test
    public void testSmallMultiLine() {
        File test = new File("test.yml");
        Configuration config = Configuration.load(test);

        ConfigurationSection first = config.getConfigurationSection("3");
        ConfigurationSection macros = first.getConfigurationSection("macros");

        List<String> lore = first.getStringList("lore");

        MacroToken token = new MacroToken(macros);

        List<MacroToken> tokens = new ArrayList<>();
        tokens.add(token);

        MacroParser parser = new MacroParser(tokens);

        List<String> newLore = parser.parseListMacros(lore);

        System.out.println(newLore.toString());

        assertTrue("Size of new lore is not 2", newLore.size() == 2);
        assertTrue("First line is not 'Replace some text", newLore.get(0).equals("Replace some text"));
        assertTrue("Second line is not 'test'", newLore.get(1).equals("test"));
    }

    @Test
    public void testOutOfIndexSmallMultiLine() {
        File test = new File("test.yml");
        Configuration config = Configuration.load(test);

        ConfigurationSection first = config.getConfigurationSection("4");
        ConfigurationSection macros = first.getConfigurationSection("macros");

        List<String> lore = first.getStringList("lore");

        MacroToken token = new MacroToken(macros);

        List<MacroToken> tokens = new ArrayList<>();
        tokens.add(token);

        MacroParser parser = new MacroParser(tokens);

        List<String> newLore = parser.parseListMacros(lore);

        System.out.println("New lore: " + newLore + " Size: " + newLore.size());

        assertTrue("First line is not 'Replace some text", newLore.get(0).equals("Replace some text"));

    }

    @Test
    public void testSingleLineParsing() {
        File test = new File("test.yml");
        Configuration config = Configuration.load(test);

        ConfigurationSection first = config.getConfigurationSection("2");
        ConfigurationSection macros = first.getConfigurationSection("macros");

        String name = first.getString("name");

        MacroToken token = new MacroToken(macros);

        List<MacroToken> tokens = new ArrayList<>();
        tokens.add(token);

        MacroParser parser = new MacroParser(tokens);

        String newName = parser.parseStringMacros(name);

        assertTrue("New name is not 'A name'", newName.equals("A name"));
    }

    @Test
    public void testMacroChaining() {
        File test = new File("test.yml");
        Configuration config = Configuration.load(test);

        ConfigurationSection fifth = config.getConfigurationSection("5");
        ConfigurationSection fithMacrosSection = fifth.getConfigurationSection("macros");
        ConfigurationSection guiMacrosSection = config.getConfigurationSection("macros");

        List<String> lore = fifth.getStringList("lore");

        MacroToken guiToken = new MacroToken(guiMacrosSection);
        MacroToken fifthToken = new MacroToken(fithMacrosSection);

        List<MacroToken> tokens = new ArrayList<>();
        tokens.add(guiToken);
        tokens.add(fifthToken);

        MacroParser parser = new MacroParser(tokens);

        List<String> parsedLore = parser.parseListMacros(lore);

        System.out.println("parsedLore: " + parsedLore.size());

        assertTrue("Lore length is not 5'", parsedLore.size() == 5);
        assertTrue("First line of lore is not 'not-a-macro'", parsedLore.get(0).equals("not-a-macro"));
        assertTrue("Second line of lore is not 'Replace some text'", parsedLore.get(1).equals("Replace some text"));
        assertTrue("Third line of lore is not 'and some other text test'", parsedLore.get(2).equals("and some other text test"));
        assertTrue("Forth line of lore is not 'with some other text'", parsedLore.get(3).equals("with some other text"));
        assertTrue("Fifth line of lore is not 'still-not-a-macro'", parsedLore.get(4).equals("still-not-a-macro"));
    }
}