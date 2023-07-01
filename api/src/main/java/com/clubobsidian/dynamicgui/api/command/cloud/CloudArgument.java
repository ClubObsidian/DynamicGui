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

package com.clubobsidian.dynamicgui.api.command.cloud;

import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.standard.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;

public final class CloudArgument {

    public static final String PLAYER_ARG_NAME = "player";
    public static final CloudArgument BOOLEAN = create(name -> BooleanArgument.builder(name));
    public static final CloudArgument BYTE = create(name -> ByteArgument.builder(name));
    public static final CloudArgument CHAR = create(name -> CharArgument.builder(name));
    public static final CloudArgument DOUBLE = create(name -> DoubleArgument.builder(name));
    public static final CloudArgument FLOAT = create(name -> FloatArgument.builder(name));
    public static final CloudArgument INTEGER = create(name -> IntegerArgument.builder(name));
    public static final CloudArgument LONG = create(name -> LongArgument.builder(name));
    public static final CloudArgument SHORT = create(name -> ShortArgument.builder(name));
    public static final CloudArgument STRING = create(name -> StringArgument.builder(name));
    public static final CloudArgument UUID = create(name -> UUIDArgument.builder(name));

    private static final Map<String, CloudArgument> TYPES = new HashMap<>();

    static {
        for (Field field : CloudArgument.class.getDeclaredFields()) {
            if (field.getType().equals(CloudArgument.class) && Modifier.isStatic(field.getModifiers())) {
                try {
                    register(field.getName(), (CloudArgument) field.get(null));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static CloudArgument create(@NotNull Function<String, CommandArgument.Builder> func) {
        return new CloudArgument((d) -> {
            CommandArgument.Builder builder = func.apply(d.getArgumentName());
            if (d.isOptional()) {
                builder.asOptional();
            } else {
                builder.asRequired();
            }
            return builder.build();
        });
    }

    public static void register(@NotNull String name, @NotNull CloudArgument arg) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(arg);
        TYPES.put(name.toLowerCase(Locale.ROOT), arg);
    }

    public static Optional<CloudArgument> fromType(@NotNull String type) {
        Objects.requireNonNull(type);
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
        return Optional.ofNullable(TYPES.get(type));
    }

    private final Function<CloudData, CommandArgument> function;

    public CloudArgument(@NotNull Function<CloudData, CommandArgument> function) {
        this.function = Objects.requireNonNull(function);
    }

    public <T extends CommandArgument> T argument(@NotNull String argName) {
        return this.argument(argName, false);
    }

    public <T extends CommandArgument> T argument(@NotNull String argName, boolean optional) {
        Objects.requireNonNull(argName);
        return (T) this.function.apply(new CloudData(argName, optional));
    }
}
