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
package com.clubobsidian.dynamicgui.parser.function;

import java.util.HashMap;
import java.util.Map;

import com.clubobsidian.fuzzutil.StringFuzz;

public enum FunctionType {

    CLICK(true),
    LEFT(true),
    RIGHT(true),
    MIDDLE(true),
    SHIFT_CLICK(true),
    SHIFT_LEFT(true),
    SHIFT_RIGHT(true),
    LOAD(false),
    FAIL(false),
    SWITCH_MENU(false),
    EXIT_MENU(false);

    private final boolean isClick;

    FunctionType(boolean isClick) {
        this.isClick = isClick;
    }

    public boolean isClick() {
        return this.isClick;
    }

    private final static Map<String, FunctionType> normalizedFunctions = new HashMap<>();

    static {
        for (FunctionType type : FunctionType.values()) {
            String normalized = StringFuzz.normalize(type.toString());
            normalizedFunctions.put(normalized, type);
        }
    }

    public static FunctionType getFuzzyType(String type) {
        return normalizedFunctions.get(StringFuzz.normalize(type));
    }
}