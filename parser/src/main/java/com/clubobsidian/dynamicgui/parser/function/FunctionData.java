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

import java.io.Serializable;

import com.clubobsidian.fuzzutil.StringFuzz;

public class FunctionData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3386051776711859198L;

    private final String name;
    private final String data;
    private final FunctionModifier modifier;

    public FunctionData(String name, String data, FunctionModifier modifier) {
        this.name = StringFuzz.normalize(name);
        this.data = data;
        this.modifier = modifier;
    }

    public String getName() {
        return this.name;
    }

    public String getData() {
        return this.data;
    }

    public FunctionModifier getModifier() {
        return this.modifier;
    }
}