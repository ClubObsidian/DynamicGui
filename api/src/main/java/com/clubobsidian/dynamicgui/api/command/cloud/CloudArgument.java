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

import org.incendo.cloud.component.CommandComponent;
import org.incendo.cloud.parser.standard.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;

public final class CloudArgument {

    public static final String PLAYER_ARG_NAME = "player";
    public static final CloudArgument BOOLEAN = create(name -> BooleanParser.booleanComponent().name(name));
    public static final CloudArgument BYTE = create(name -> ByteParser.byteComponent().name(name));
    public static final CloudArgument CHAR = create(name -> CharacterParser.characterComponent().name(name));
    public static final CloudArgument DOUBLE = create(name -> DoubleParser.doubleComponent().name(name));
    public static final CloudArgument FLOAT = create(name -> FloatParser.floatComponent().name(name));
    public static final CloudArgument INTEGER = create(name -> IntegerParser.integerComponent().name(name));
    public static final CloudArgument LONG = create(name -> LongParser.longComponent().name(name));
    public static final CloudArgument SHORT = create(name -> ShortParser.shortComponent().name(name));
    public static final CloudArgument STRING = create(name -> StringParser.stringComponent().name(name));
    public static final CloudArgument UUID = create(name -> UUIDParser.uuidComponent().name(name));

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

    public static CloudArgument create(@NotNull Function<String, CommandComponent.Builder> func) {
        return new CloudArgument((d) -> {
            CommandComponent.Builder builder = func.apply(d.getArgumentName());
            if (d.isOptional()) {
                builder.optional();
            } else {
                builder.required();
            }
            if (d.isGreedy()) {
                builder.parser(StringParser.greedyStringParser());
            }
            return builder;
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

    private final Function<CloudData, CommandComponent.Builder> function;

    public CloudArgument(@NotNull Function<CloudData, CommandComponent.Builder> function) {
        this.function = Objects.requireNonNull(function);
    }

    public <T extends CommandComponent.Builder> T argument(@NotNull String argName) {
        return this.argument(argName, false, false);
    }

    public <T extends CommandComponent.Builder> T argument(@NotNull String argName,
                                                   boolean optional,
                                                   boolean greedy) {
        Objects.requireNonNull(argName);
        return (T) this.function.apply(new CloudData(argName, optional, greedy));
    }
}
