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

import com.clubobsidian.dynamicgui.api.parser.macro.MacroToken;
import com.clubobsidian.dynamicgui.parser.macro.SimpleMacroToken;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MacrosTokenTest {

    @Test
    public void testGuiMacroToken() {
        File file = new File("test.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection section = config
                .getConfigurationSection("macros");
        MacroToken token = new SimpleMacroToken(section);
        Map<String, Object> macros = token.getMacros();

        assertEquals(2, macros.size());
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

        MacroToken token = new SimpleMacroToken(section);
        Map<String, Object> macros = token.getMacros();

        Object firstMacro = macros.get("%test%");
        assertTrue(firstMacro instanceof String);
        assertEquals("This is some text", firstMacro);

        Object secondMacro = macros.get("%multiline-test%");
        assertTrue(secondMacro instanceof List);

        List listMacro = ((List) secondMacro);

        assertTrue(listMacro.get(0) instanceof String);
        assertEquals(3, listMacro.size());

    }

    @Test
    public void testEmptyMacroToken() {
        File file = new File("test.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection section = config
                .getConfigurationSection("1")
                .getConfigurationSection("macros");

        MacroToken token = new SimpleMacroToken(section);
        Map<String, Object> macros = token.getMacros();

        assertNotNull(macros);
        assertEquals(0, macros.size());
    }
}