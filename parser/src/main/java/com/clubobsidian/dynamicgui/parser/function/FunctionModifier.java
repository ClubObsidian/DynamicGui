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

public enum FunctionModifier {

    NOT("!"),
    NONE("");

    private final String modifier;

    FunctionModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getModifier() {
        return this.modifier;
    }

    public static FunctionModifier findModifier(String functionData) {
        for (FunctionModifier modifier : FunctionModifier.values()) {
            if (modifier != FunctionModifier.NONE) {
                if (functionData.startsWith(modifier.getModifier())) {
                    return modifier;
                }
            }
        }
        return FunctionModifier.NONE;
    }
}