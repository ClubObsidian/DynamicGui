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

import com.clubobsidian.dynamicgui.parser.macro.MacroParser;
import com.clubobsidian.dynamicgui.parser.macro.MacroToken;
import com.clubobsidian.wrappy.Configuration;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiMacroSameFileTest {

    @Test
    public void stringTest() {
        File testFolder = new File("test");
        File macroFolder = new File(testFolder, "macro");
        File sameFileMacroFile = new File(macroFolder, "multi-same-file-macro.yml");
        System.out.println(sameFileMacroFile.exists());
        Configuration config = Configuration.load(sameFileMacroFile);

        List<MacroToken> tokens = new ArrayList<>();
        for (String key : config.getKeys()) {
            MacroToken token = new MacroToken(config.getConfigurationSection(key));
            tokens.add(token);
        }

        MacroParser parser = new MacroParser(tokens);
        String toParse = "%uses-test%";
        String parsed = parser.parseStringMacros(toParse);
        System.out.println(parsed);
        assertEquals("test", parsed);
    }

    @Test
    public void listTest() {
        File testFolder = new File("test");
        File macroFolder = new File(testFolder, "macro");
        File sameFileMacroFile = new File(macroFolder, "multi-same-file-macro.yml");
        System.out.println(sameFileMacroFile.exists());
        Configuration config = Configuration.load(sameFileMacroFile);

        List<MacroToken> tokens = new ArrayList<>();
        for (String key : config.getKeys()) {
            MacroToken token = new MacroToken(config.getConfigurationSection(key));
            tokens.add(token);
        }

        MacroParser parser = new MacroParser(tokens);
        List<String> replaceIn = new ArrayList<>();
        String toParse = "%uses-multiline-macro%";
        replaceIn.add(toParse);
        List<String> parsed = parser.parseListMacros(replaceIn);
        System.out.println(parsed);
        assertEquals(2, parsed.size());
        assertEquals("test1", parsed.get(0));
        assertEquals("test2", parsed.get(1));
    }

}