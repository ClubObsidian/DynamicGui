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

package com.clubobsidian.dynamicgui.core.factory;

import com.clubobsidian.dynamicgui.api.factory.FunctionTokenFactory;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionData;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionToken;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.parser.function.SimpleFunctionToken;

import java.util.List;

public class FunctionTokenFactoryImpl implements FunctionTokenFactory {

    @Override
    public FunctionToken create(String name,
                                List<FunctionType> types,
                                List<FunctionData> functions,
                                List<FunctionData> failFunctions) {
        return new SimpleFunctionToken(name, types, functions, failFunctions);
    }
}
