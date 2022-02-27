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

import com.clubobsidian.dynamicgui.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.parser.function.FunctionTypeParser;
import com.clubobsidian.dynamicgui.parser.macro.MacroParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FunctionTypeParserTest {

    @Test
    public void testLeft() {
        MacroParser macroParser = new MacroParser(new ArrayList<>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertEquals(typeParser.parseType("LEFT"), FunctionType.LEFT);
    }

    @Test
    public void testRight() {
        MacroParser macroParser = new MacroParser(new ArrayList<>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertEquals(typeParser.parseType("RIGHT"), FunctionType.RIGHT);
    }

    @Test
    public void testLowercase() {
        MacroParser macroParser = new MacroParser(new ArrayList<>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertEquals(typeParser.parseType("left"), FunctionType.LEFT);
    }

    @Test
    public void testParseShiftLeftWithUnderscore() {
        MacroParser macroParser = new MacroParser(new ArrayList<>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertEquals(typeParser.parseType("SHIFT_LEFT"), FunctionType.SHIFT_LEFT);
    }

    @Test
    public void testParseShiftLeftNoUnderscore() {
        MacroParser macroParser = new MacroParser(new ArrayList<>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertEquals(typeParser.parseType("SHIFTLEFT"), FunctionType.SHIFT_LEFT);
    }

    @Test
    public void testParseShiftRighWithUnderscore() {
        MacroParser macroParser = new MacroParser(new ArrayList<>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertEquals(typeParser.parseType("SHIFT_RIGHT"), FunctionType.SHIFT_RIGHT);
    }

    @Test
    public void testParseShiftRightNoUnderscore() {
        MacroParser macroParser = new MacroParser(new ArrayList<>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertEquals(typeParser.parseType("SHIFTRIGHT"), FunctionType.SHIFT_RIGHT);
    }

    @Test
    public void testInvalidFunctionType() {
        MacroParser macroParser = new MacroParser(new ArrayList<>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertNull(typeParser.parseType("DOESNOTEXIST"));
    }

    @Test
    public void testFunctionTypeList() {
        MacroParser macroParser = new MacroParser(new ArrayList<>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        List<String> types = new ArrayList<>(Arrays.asList("LEFT", "RIGHT"));
        List<FunctionType> parsedTypes = typeParser.parseTypes(types);
        assertEquals(2, parsedTypes.size());
    }

    @Test
    public void testFunctionTypeListWithInvalidFunction() {
        MacroParser macroParser = new MacroParser(new ArrayList<>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        List<String> types = new ArrayList<>(Arrays.asList("LEFT", "DOESNOTEXIST"));
        List<FunctionType> parsedTypes = typeParser.parseTypes(types);
        assertEquals(1, parsedTypes.size());
    }
}