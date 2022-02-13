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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.clubobsidian.dynamicgui.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.parser.function.FunctionTypeParser;
import com.clubobsidian.dynamicgui.parser.macro.MacroParser;
import com.clubobsidian.dynamicgui.parser.macro.MacroToken;

public class FunctionTypeParserTest {

    @Test
    public void testLeft() {
        MacroParser macroParser = new MacroParser(new ArrayList<MacroToken>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertTrue(typeParser.parseType("LEFT").equals(FunctionType.LEFT));
    }

    @Test
    public void testRight() {
        MacroParser macroParser = new MacroParser(new ArrayList<MacroToken>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertTrue(typeParser.parseType("RIGHT").equals(FunctionType.RIGHT));
    }

    @Test
    public void testLowercase() {
        MacroParser macroParser = new MacroParser(new ArrayList<MacroToken>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertTrue(typeParser.parseType("left").equals(FunctionType.LEFT));
    }

    @Test
    public void testParseShiftLeftWithUnderscore() {
        MacroParser macroParser = new MacroParser(new ArrayList<MacroToken>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertTrue(typeParser.parseType("SHIFT_LEFT").equals(FunctionType.SHIFT_LEFT));
    }

    @Test
    public void testParseShiftLeftNoUnderscore() {
        MacroParser macroParser = new MacroParser(new ArrayList<MacroToken>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertTrue(typeParser.parseType("SHIFTLEFT").equals(FunctionType.SHIFT_LEFT));
    }

    @Test
    public void testParseShiftRighWithUnderscore() {
        MacroParser macroParser = new MacroParser(new ArrayList<MacroToken>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertTrue(typeParser.parseType("SHIFT_RIGHT").equals(FunctionType.SHIFT_RIGHT));
    }

    @Test
    public void testParseShiftRightNoUnderscore() {
        MacroParser macroParser = new MacroParser(new ArrayList<MacroToken>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertTrue(typeParser.parseType("SHIFTRIGHT").equals(FunctionType.SHIFT_RIGHT));
    }

    @Test
    public void testInvalidFunctionType() {
        MacroParser macroParser = new MacroParser(new ArrayList<MacroToken>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        assertTrue(typeParser.parseType("DOESNOTEXIST") == null);
    }

    @Test
    public void testFunctionTypeList() {
        MacroParser macroParser = new MacroParser(new ArrayList<MacroToken>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        List<String> types = new ArrayList<>(Arrays.asList("LEFT", "RIGHT"));
        List<FunctionType> parsedTypes = typeParser.parseTypes(types);
        assertTrue(parsedTypes.size() == 2);
    }

    @Test
    public void testFunctionTypeListWithInvalidFunction() {
        MacroParser macroParser = new MacroParser(new ArrayList<MacroToken>());
        FunctionTypeParser typeParser = new FunctionTypeParser(macroParser);
        List<String> types = new ArrayList<>(Arrays.asList("LEFT", "DOESNOTEXIST"));
        List<FunctionType> parsedTypes = typeParser.parseTypes(types);
        assertTrue(parsedTypes.size() == 1);
    }
}