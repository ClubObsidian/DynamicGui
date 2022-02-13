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
import java.util.ArrayList;
import java.util.List;

import com.clubobsidian.dynamicgui.parser.macro.MacroParser;

public class FunctionTypeParser implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -496196602929082383L;

    private final MacroParser macroParser;

    public FunctionTypeParser(MacroParser macroParser) {
        this.macroParser = macroParser;
    }

    public List<FunctionType> parseTypes(List<String> types) {
        types = this.macroParser.parseListMacros(types);
        List<FunctionType> typesList = new ArrayList<>();
        for (String type : types) {
            FunctionType parsedType = this.parseType(type);
            if (parsedType == null)
                continue; //TODO - warn

            typesList.add(parsedType);
        }
        return typesList;
    }

    public FunctionType parseType(String type) {
        return FunctionType.getFuzzyType(type);
    }
}