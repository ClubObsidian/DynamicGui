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

package com.clubobsidian.dynamicgui.core.test.command;

import cloud.commandframework.arguments.standard.BooleanArgument;
import cloud.commandframework.arguments.standard.ByteArgument;
import cloud.commandframework.arguments.standard.CharArgument;
import cloud.commandframework.arguments.standard.DoubleArgument;
import cloud.commandframework.arguments.standard.FloatArgument;
import cloud.commandframework.arguments.standard.IntegerArgument;
import cloud.commandframework.arguments.standard.LongArgument;
import cloud.commandframework.arguments.standard.ShortArgument;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.arguments.standard.UUIDArgument;
import com.clubobsidian.dynamicgui.core.command.cloud.CloudArgument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class CloudArgumentTest {

    @Test
    public void testFromTypeText() {
        assertEquals(CloudArgument.STRING, CloudArgument.fromType("text").get());
    }

    @Test
    public void testFromTypeNumber() {
        assertEquals(CloudArgument.INTEGER, CloudArgument.fromType("number").get());
    }

    @Test
    public void testBooleanArgument() {
        assertInstanceOf(BooleanArgument.class, CloudArgument.BOOLEAN.argument("test"));
    }

    @Test
    public void testByteArgument() {
        assertInstanceOf(ByteArgument.class, CloudArgument.BYTE.argument("test"));
    }

    @Test
    public void testCharArgument() {
        assertInstanceOf(CharArgument.class, CloudArgument.CHAR.argument("test"));
    }

    @Test
    public void testDoubleArgument() {
        assertInstanceOf(DoubleArgument.class, CloudArgument.DOUBLE.argument("test"));
    }

    @Test
    public void testFloatArgument() {
        assertInstanceOf(FloatArgument.class, CloudArgument.FLOAT.argument("test"));
    }

    @Test
    public void testIntegerArgument() {
        assertInstanceOf(IntegerArgument.class, CloudArgument.INTEGER.argument("test"));
    }

    @Test
    public void testLongArgument() {
        assertInstanceOf(LongArgument.class, CloudArgument.LONG.argument("test"));
    }

    @Test
    public void testShortArgument() {
        assertInstanceOf(ShortArgument.class, CloudArgument.SHORT.argument("test"));
    }

    @Test
    public void testStringArgument() {
        assertInstanceOf(StringArgument.class, CloudArgument.STRING.argument("test"));
    }

    @Test
    public void testUUIDArgument() {
        assertInstanceOf(UUIDArgument.class, CloudArgument.UUID.argument("test"));
    }
}