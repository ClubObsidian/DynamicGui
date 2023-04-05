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

package com.clubobsidian.dynamicgui.parser.function;

import com.clubobsidian.dynamicgui.api.parser.function.FunctionData;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionModifier;
import com.clubobsidian.fuzzutil.StringFuzz;

public class SimpleFunctionData implements FunctionData {

    /**
     *
     */
    private static final long serialVersionUID = 3386051776711859198L;

    private final String name;
    private final String data;
    private final FunctionModifier modifier;

    public SimpleFunctionData(String name, String data, FunctionModifier modifier) {
        this.name = StringFuzz.normalize(name);
        this.data = data;
        this.modifier = modifier;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getData() {
        return this.data;
    }

    @Override
    public FunctionModifier getModifier() {
        return this.modifier;
    }
}