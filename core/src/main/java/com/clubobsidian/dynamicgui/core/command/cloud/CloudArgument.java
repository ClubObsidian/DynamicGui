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

package com.clubobsidian.dynamicgui.core.command.cloud;

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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public enum CloudArgument {

    //TODO - implement enum & string_array
    BOOLEAN((Function<String, BooleanArgument>) s -> (BooleanArgument) BooleanArgument.of(s)),
    BYTE((Function<String, ByteArgument>) s -> (ByteArgument) ByteArgument.of(s)),
    CHAR((Function<String, CharArgument>) s -> (CharArgument) CharArgument.of(s)),
    DOUBLE((Function<String, DoubleArgument>) s -> (DoubleArgument) DoubleArgument.of(s)),
    FLOAT((Function<String, FloatArgument>) s -> (FloatArgument) FloatArgument.of(s)),
    INTEGER((Function<String, IntegerArgument>) s -> (IntegerArgument) IntegerArgument.of(s)),
    LONG((Function<String, LongArgument>) s -> (LongArgument) LongArgument.of(s)),
    SHORT((Function<String, ShortArgument>) s -> (ShortArgument) ShortArgument.of(s)),
    STRING((Function<String, StringArgument>) s -> (StringArgument) StringArgument.of(s)),
    UUID((Function<String, UUIDArgument>) s -> (UUIDArgument) UUIDArgument.of(s));

    private static final Map<String, CloudArgument> types = new HashMap<>();

    static {
        for (CloudArgument arg : CloudArgument.values()) {
            types.put(arg.name().toLowerCase(Locale.ROOT), arg);
        }
    }

    public static Optional<CloudArgument> fromType(String type) {
        type = type.toLowerCase(Locale.ROOT);
        switch (type) {
            case "text":
                type = "string";
                break;
            case "number":
                type = "integer";
                break;
            default:
                break;
        }
        return Optional.ofNullable(types.get(type));
    }

    private final Function<String, ?> function;

    CloudArgument(Function<String, ?> function) {
        this.function = function;
    }

    public <T extends CommandArgument> T argument(String arg) {
        return (T) this.function.apply(arg);
    }
}
