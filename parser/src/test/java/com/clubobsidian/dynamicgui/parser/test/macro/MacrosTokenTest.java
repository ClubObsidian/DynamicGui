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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.clubobsidian.dynamicgui.parser.macro.MacroToken;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;

public class MacrosTokenTest {

    @Test
    public void testGuiMacroToken() {
        File file = new File("test.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection section = config
                .getConfigurationSection("macros");
        MacroToken token = new MacroToken(section);
        Map<String, Object> macros = token.getMacros();

        assertTrue(macros.size() == 2);
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void testSlotMacroToken() {
        File file = new File("test.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection section = config
                .getConfigurationSection("2")
                .getConfigurationSection("macros");

        System.out.println(section.getKeys());

        MacroToken token = new MacroToken(section);
        Map<String, Object> macros = token.getMacros();

        Object firstMacro = macros.get("%test%");
        assertTrue(firstMacro instanceof String);
        assertTrue(firstMacro.equals("This is some text"));

        Object secondMacro = macros.get("%multiline-test%");
        assertTrue(secondMacro instanceof List);

        List listMacro = ((List) secondMacro);

        assertTrue(listMacro.get(0) instanceof String);
        assertTrue(listMacro.size() == 3);

    }

    @Test
    public void testEmptyMacroToken() {
        File file = new File("test.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection section = config
                .getConfigurationSection("1")
                .getConfigurationSection("macros");

        MacroToken token = new MacroToken(section);
        Map<String, Object> macros = token.getMacros();

        assertTrue(macros != null);
        assertTrue(macros.size() == 0);
    }
}