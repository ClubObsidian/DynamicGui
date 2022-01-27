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
import java.util.List;

public class FunctionToken implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1157621981134953861L;

    private final String name;
    private final List<FunctionType> types;
    private final List<FunctionData> functions;
    private final List<FunctionData> failOnFunctions;

    public FunctionToken(String name, List<FunctionType> types, List<FunctionData> functions, List<FunctionData> failOnFunctions) {
        this.name = name;
        this.types = types;
        this.functions = functions;
        this.failOnFunctions = failOnFunctions;
    }

    public String getName() {
        return this.name;
    }

    public List<FunctionType> getTypes() {
        return this.types;
    }

    public List<FunctionData> getFunctions() {
        return this.functions;
    }

    public List<FunctionData> getFailOnFunctions() {
        return this.failOnFunctions;
    }
}