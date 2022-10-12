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

package com.clubobsidian.dynamicgui.api.manager;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.api.function.FunctionOwner;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionType;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class FunctionManager {

    @Inject
    private static FunctionManager instance;

    public static FunctionManager get() {
        return instance;
    }

    public abstract Function getFunctionByName(String functionName);

    public abstract List<Function> getFunctions();

    public abstract boolean addFunction(Function function);

    public abstract boolean removeFunctionByName(String functionName);

    public abstract boolean hasAsyncFunctionRunning(PlayerWrapper<?> playerWrapper);

    public abstract boolean hasAsyncFunctionRunning(UUID uuid);

    public abstract boolean hasAsyncFunctionRunning(PlayerWrapper<?> playerWrapper, String functionName);

    public abstract boolean hasAsyncFunctionRunning(UUID uuid, String functionName);

    public abstract CompletableFuture<Boolean> tryFunctions(FunctionOwner owner,
                                                   FunctionType type,
                                                   PlayerWrapper<?> playerWrapper);

}