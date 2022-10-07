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

package test;

import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.api.parser.macro.MacroToken;
import com.clubobsidian.dynamicgui.api.parser.slot.SlotToken;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SlotTokenTest {

    private static SlotToken token;

    @BeforeAll
    public static void loadSlotToken() {
        File file = new File("test.yml");
        Configuration config = Configuration.load(file);
        ConfigurationSection section = config.getConfigurationSection("1");
        token = new SlotToken(1, section);
    }

    @Test
    public void testSlotName() {
        String name = token.getName();
        assertEquals("test", name);
    }

    @Test
    public void testSlotIcon() {
        String icon = token.getIcon();
        assertEquals("DIRT", icon);
    }

    @Test
    public void testSlotNbt() {
        String nbt = token.getNbt();
        assertEquals("{SomeKey:\"some value\"}", nbt);
    }

    @Test
    public void testSlotGlow() {
        boolean glow = token.getGlow();
        assertFalse(glow);
    }

    @Test
    public void testSlotClose() {
        boolean close = token.isClosed();
        assertTrue(close);
    }

    @Test
    public void testSlotData() {
        byte data = token.getData();
        assertEquals(1, data);
    }


    @Test
    public void testSlotFunctionTree() {
        FunctionTree tree = token.getFunctionTree();
        int nodeSize = tree.getRootNodes().size();
        assertEquals(1, nodeSize);
    }

    @Test
    public void testMacroToken() {
        MacroToken macroToken = token.getMacroParser().getTokens().get(0);
        assertNotNull(macroToken);
    }
}