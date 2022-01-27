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
package com.clubobsidian.dynamicgui.parser.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.clubobsidian.dynamicgui.parser.function.FunctionData;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionNode;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.parser.gui.GuiMode;
import com.clubobsidian.dynamicgui.parser.gui.GuiToken;
import com.clubobsidian.dynamicgui.parser.macro.MacroToken;
import com.clubobsidian.dynamicgui.parser.slot.SlotToken;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;

public class GuiTokenTest {

    private static GuiToken token;

    @BeforeClass
    public static void loadToken() {
        File file = new File("test.yml");
        Configuration config = Configuration.load(file);
        token = new GuiToken(config);
    }

    @Test
    public void testTitle() {
        String title = token.getTitle();
        assertTrue("Gui title is not 'test gui title'", title.equals("test gui title"));
    }

    @Test
    public void testRows() {
        int rows = token.getRows();
        assertTrue("Gui rows is not 1", rows == 1);
    }

    @Test
    public void testMode() {
        GuiMode mode = token.getMode();
        assertTrue("Gui mode is not 'set'", mode == GuiMode.SET);
    }

    @Test
    public void testClose() {
        boolean closed = token.isClosed();
        assertTrue("Gui close it not true", closed);
    }

    @Test
    public void testNpcs() {
        Map<String, List<Integer>> npcs = token.getNpcs();
        List<Integer> npcIds = npcs.get("citizens");
        assertTrue("No npc ids were found for citizens", npcIds != null);
        assertTrue("Npcs size is not 2", npcIds.size() == 2);
        assertTrue("Npc at index 0 is not 5", npcIds.get(0) == 5);
        assertTrue("Npc at index 1 is not 88", npcIds.get(1) == 77);
    }

    @Test
    public void testSlots() {
        Map<Integer, SlotToken> slots = token.getSlots();
        assertTrue("Slots size is not 6", slots.size() == 6);
    }

    @Test
    public void testGuiFunctions() {
        FunctionTree tree = token.getFunctions();
        FunctionNode node = tree.getRootNodes().get(0);
        FunctionData data = node.getToken().getFunctions().get(0);
        String functionName = data.getName();
        assertTrue("Function name is not function2", functionName.equals("function2"));
    }

    @Test
    public void testMacroToken() {
        MacroToken macroToken = token.getMacroParser().getTokens().get(0);
        assertTrue("Macro token was not initialized", macroToken != null);
    }

    @Test
    public void testExternalMacroToken() {
        File externalFile = new File("external.yml");
        Configuration externalConfig = Configuration.load(externalFile);
        ConfigurationSection externalMacros = externalConfig.getConfigurationSection("macros");
        MacroToken externalToken = new MacroToken(externalMacros);

        List<MacroToken> tokens = new ArrayList<>();
        tokens.add(externalToken);

        File textFile = new File("external-test.yml");
        Configuration config = Configuration.load(textFile);
        GuiToken token = new GuiToken(config, tokens);

        String title = token.getTitle();

        assertTrue("External gui test's title is not 'test gui title'", title.equals("test gui title"));
    }
}