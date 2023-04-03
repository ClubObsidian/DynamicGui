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

package com.clubobsidian.dynamicgui.core.test.command;

import cloud.commandframework.arguments.CommandArgument;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CloudArgumentTest {

    private String getName(CommandArgument arg) {
        return arg.getValueType().getType().getTypeName();
    }

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
        assertEquals(Boolean.class.getName(), this.getName(CloudArgument.BOOLEAN.argument("test")));
    }

    @Test
    public void testByteArgument() {
        assertEquals(Byte.class.getName(), this.getName(CloudArgument.BYTE.argument("test")));
    }

    @Test
    public void testCharArgument() {
        assertEquals(Character.class.getName(), this.getName(CloudArgument.CHAR.argument("test")));
    }

    @Test
    public void testDoubleArgument() {
        assertEquals(Double.class.getName(), this.getName(CloudArgument.DOUBLE.argument("test")));
    }

    @Test
    public void testFloatArgument() {
        assertEquals(Float.class.getName(), this.getName(CloudArgument.FLOAT.argument("test")));
    }

    @Test
    public void testIntegerArgument() {
        assertEquals(Integer.class.getName(), this.getName(CloudArgument.INTEGER.argument("test")));
    }

    @Test
    public void testLongArgument() {
        assertEquals(Long.class.getName(), this.getName(CloudArgument.LONG.argument("test")));
    }

    @Test
    public void testShortArgument() {
        assertEquals(Short.class.getName(), this.getName(CloudArgument.SHORT.argument("test")));
    }

    @Test
    public void testStringArgument() {
        assertEquals(String.class.getName(), this.getName(CloudArgument.STRING.argument("test")));
    }

    @Test
    public void testUUIDArgument() {
        assertEquals(UUID.class.getName(), this.getName(CloudArgument.UUID.argument("test")));
    }
}