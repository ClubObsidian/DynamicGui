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

package com.clubobsidian.dynamicgui.api.manager;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.api.function.FunctionOwner;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class FunctionManager {

    @Inject
    private static FunctionManager instance;

    public static FunctionManager get() {
        return instance;
    }


    /**
     * Gets a registered function
     *
     * @param functionName function name to get
     * @return the function if registered or null
     */
    public abstract @Nullable Function getFunction(@NotNull String functionName);

    public abstract @Unmodifiable Collection<Function> getFunctions();

    /**
     * Registers a function
     *
     * @param function function to register
     * @return if the function was registered
     */
    public abstract boolean registerFunction(@NotNull Function function);

    /**
     * Unregisters a function
     *
     * @param function the function to unregister
     * @return if the function was unregistered
     */
    public boolean unregisterFunction(@NotNull Function function) {
        Objects.requireNonNull(function);
        return this.unregisterFunction(function.getName());
    }

    /**
     * Unregisters a function
     *
     * @param functionName name of the function to unregister
     * @return if the function was unregistered
     */
    public abstract boolean unregisterFunction(@NotNull String functionName);

    public abstract boolean hasAsyncFunctionRunning(@NotNull PlayerWrapper<?> playerWrapper);

    public abstract boolean hasAsyncFunctionRunning(@NotNull UUID uuid);

    public abstract boolean hasAsyncFunctionRunning(@NotNull PlayerWrapper<?> playerWrapper,
                                                    @NotNull String functionName);

    public abstract boolean hasAsyncFunctionRunning(@NotNull UUID uuid, @NotNull String functionName);

    public abstract CompletableFuture<Boolean> tryFunctions(@NotNull FunctionOwner owner,
                                                            @NotNull FunctionType type,
                                                            @NotNull PlayerWrapper<?> playerWrapper);

}