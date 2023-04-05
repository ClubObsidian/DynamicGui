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

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionTypeParser;
import com.clubobsidian.dynamicgui.api.parser.macro.MacroParser;

import java.util.ArrayList;
import java.util.List;

public class SimpleFunctionTypeParser implements FunctionTypeParser {

    /**
     *
     */
    private static final long serialVersionUID = -496196602929082383L;

    private final MacroParser macroParser;

    public SimpleFunctionTypeParser(MacroParser macroParser) {
        this.macroParser = macroParser;
    }

    @Override
    public List<FunctionType> parseTypes(List<String> types) {
        types = this.macroParser.parseListMacros(types);
        List<FunctionType> typesList = new ArrayList<>();
        for (String type : types) {
            FunctionType parsedType = this.parseType(type);
            if (parsedType == null) {
                DynamicGui.get().getLogger().error("Invalid function type %s", type);
                continue;
            }
            typesList.add(parsedType);
        }
        return typesList;
    }
}