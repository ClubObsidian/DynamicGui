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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public enum CloudArgument {

    //TODO - implement enum & string_array
    BOOLEAN((Function<CloudData, CommandArgument>) d -> CloudUtil.createArg(Boolean.class, d)),
    BYTE((Function<CloudData, CommandArgument>) d -> CloudUtil.createArg(Byte.class, d)),
    CHAR((Function<CloudData, CommandArgument>) d -> CloudUtil.createArg(Character.class, d)),
    DOUBLE((Function<CloudData, CommandArgument>) d -> CloudUtil.createArg(Double.class, d)),
    FLOAT((Function<CloudData, CommandArgument>) d -> CloudUtil.createArg(Float.class, d)),
    INTEGER((Function<CloudData, CommandArgument>) d -> CloudUtil.createArg(Integer.class, d)),
    LONG((Function<CloudData, CommandArgument>) d -> CloudUtil.createArg(Long.class, d)),
    SHORT((Function<CloudData, CommandArgument>) d -> CloudUtil.createArg(Short.class, d)),
    STRING((Function<CloudData, CommandArgument>) d -> CloudUtil.createArg(String.class, d)),
    UUID((Function<CloudData, CommandArgument>) d -> CloudUtil.createArg(UUID.class, d));

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

    private final Function<CloudData, ?> function;

    CloudArgument(Function<CloudData, ?> function) {
        this.function = function;
    }

    public <T extends CommandArgument> T argument(String arg) {
        return this.argument(arg, false);
    }

    public <T extends CommandArgument> T argument(String arg, boolean optional) {
        return (T) this.function.apply(new CloudData(arg, optional));
    }
}
