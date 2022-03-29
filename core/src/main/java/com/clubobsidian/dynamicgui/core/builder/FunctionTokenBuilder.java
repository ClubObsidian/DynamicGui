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

package com.clubobsidian.dynamicgui.core.builder;

import com.clubobsidian.dynamicgui.parser.function.FunctionData;
import com.clubobsidian.dynamicgui.parser.function.FunctionModifier;
import com.clubobsidian.dynamicgui.parser.function.FunctionToken;
import com.clubobsidian.dynamicgui.parser.function.FunctionType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FunctionTokenBuilder {

    private final String name = UUID.randomUUID().toString();
    private final List<FunctionType> types = new ArrayList<>();
    private final List<FunctionData> functions = new ArrayList<>();
    private final List<FunctionData> failOnFunctions = new ArrayList<>();

    public FunctionTokenBuilder addType(String type) {
        FunctionType functionType = FunctionType.valueOf(type.toUpperCase());
        this.types.add(functionType);
        return this;
    }

    public FunctionTokenBuilder addType(String... types) {
        for (String t : types) {
            this.addType(t);
        }
        return this;
    }

    public FunctionTokenBuilder addFunction(String name, String data) {
        return this.addFunction(name, data, FunctionModifier.NONE);
    }

    public FunctionTokenBuilder addFunction(String name, String data, FunctionModifier modifier) {
        return this.addFunction(new FunctionData(name, data, modifier));
    }

    public FunctionTokenBuilder addFunction(FunctionData data) {
        this.functions.add(data);
        return this;
    }

    public FunctionTokenBuilder addFunction(FunctionData... datas) {
        for (FunctionData data : datas) {
            this.addFunction(data);
        }

        return this;
    }

    public FunctionTokenBuilder addFailOnFunction(String name, String data) {
        return this.addFailOnFunction(name, data, FunctionModifier.NONE);
    }

    public FunctionTokenBuilder addFailOnFunction(String name, String data, FunctionModifier modifier) {
        return this.addFailOnFunction(new FunctionData(name, data, modifier));
    }

    public FunctionTokenBuilder addFailOnFunction(FunctionData data) {
        this.failOnFunctions.add(data);
        return this;
    }

    public FunctionTokenBuilder addFailOnFunction(FunctionData... datas) {
        for (FunctionData data : datas) {
            this.addFunction(data);
        }

        return this;
    }

    public FunctionToken build() {
        return new FunctionToken(this.name, this.types, this.functions, this.failOnFunctions);
    }
}