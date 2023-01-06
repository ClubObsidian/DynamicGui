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

package com.clubobsidian.dynamicgui.core.manager;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.api.function.FunctionOwner;
import com.clubobsidian.dynamicgui.api.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.api.manager.FunctionManager;
import com.clubobsidian.dynamicgui.api.manager.replacer.ReplacerManager;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionData;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionModifier;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionToken;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionNode;
import com.clubobsidian.dynamicgui.core.util.ThreadUtil;
import com.clubobsidian.fuzzutil.StringFuzz;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FunctionManagerImpl extends FunctionManager {

    private final Map<String, Function> functions = new HashMap<>();
    private final Map<UUID, Map<Function, AtomicInteger>> runningAsyncFunctions = new ConcurrentHashMap<>();
    private final LoggerWrapper<?> logger;

    @Inject
    private FunctionManagerImpl(LoggerWrapper<?> logger) {
        this.logger = logger;
    }

    @Override
    public Function getFunctionByName(String functionName) {
        String normalized = StringFuzz.normalize(functionName);
        Function function = this.functions.get(normalized);
        if (function == null) {
            return null;
        }
        return function.clone();
    }

    @Override
    public Collection<Function> getFunctions() {
        return Collections.unmodifiableCollection(this.functions.values());
    }

    @Override
    public boolean registerFunction(@NotNull Function function) {
        Objects.nonNull(function);
        boolean wasNotNull = this.functions.put(function.getName(), function) != null;
        for (String alias : function.getAliases()) {
            if (this.functions.put(alias, function) != null) {
                wasNotNull = true;
            }
        }
        return wasNotNull;
    }


    @Override
    public boolean unregisterFunction(@NotNull String functionName) {
        Objects.requireNonNull(functionName);
        String normalized = StringFuzz.normalize(functionName);
        return this.functions.remove(normalized) != null;
    }

    @Override
    public boolean hasAsyncFunctionRunning(PlayerWrapper<?> playerWrapper) {
        return this.hasAsyncFunctionRunning(playerWrapper.getUniqueId());
    }

    @Override
    public boolean hasAsyncFunctionRunning(UUID uuid) {
        Map<Function, AtomicInteger> running = this.runningAsyncFunctions.get(uuid);
        return running != null && running.size() > 0;
    }

    @Override
    public boolean hasAsyncFunctionRunning(PlayerWrapper<?> playerWrapper, String functionName) {
        return this.hasAsyncFunctionRunning(playerWrapper.getUniqueId(), functionName);
    }

    @Override
    public boolean hasAsyncFunctionRunning(UUID uuid, String functionName) {
        Function function = this.functions.get(functionName);
        Map<Function, AtomicInteger> functionMap = this.runningAsyncFunctions.get(uuid);
        AtomicInteger num = functionMap == null ? null : functionMap.get(function);
        return function != null
                && function.isAsync()
                && functionMap != null
                && num != null
                && num.get() != 0;
    }

    @Override
    public CompletableFuture<Boolean> tryFunctions(FunctionOwner owner, FunctionType type, PlayerWrapper<?> playerWrapper) {
        CompletableFuture<Boolean> returnFuture = new CompletableFuture<>();
        returnFuture.exceptionally((ex) -> {
            ex.printStackTrace();
            return null;
        });
        List<FunctionNode> rootNodes = owner.getFunctions().getRootNodes();
        int rootSize = rootNodes.size();
        if (rootSize == 0) {
            returnFuture.complete(true);
        } else {
            AtomicBoolean returnValue = new AtomicBoolean(true);
            AtomicInteger count = new AtomicInteger();
            for (int i = 0; i < rootSize; i++) {
                FunctionNode node = rootNodes.get(i);
                CompletableFuture<Boolean> future = new CompletableFuture<>();
                future.whenComplete((ret, ex) -> {
                    if (ex != null) {
                        ex.printStackTrace();
                        returnValue.set(false);
                    } else if (!ret) {
                        returnValue.set(false);
                    }
                    int incremented = count.incrementAndGet();
                    if (incremented == rootSize) {
                        returnFuture.complete(returnValue.get());
                    }
                });
                recurFunctionNodes(null,
                        owner,
                        Collections.singletonList(node),
                        type,
                        playerWrapper,
                        future,
                        new AtomicBoolean(true));
            }
        }
        return returnFuture;
    }

    private void recurFunctionNodes(FunctionResponse response,
                                    FunctionOwner owner,
                                    List<FunctionNode> functionNodes,
                                    FunctionType type,
                                    PlayerWrapper<?> playerWrapper,
                                    CompletableFuture<Boolean> future,
                                    AtomicBoolean returnValue) {
        if (functionNodes.size() == 0 || !hasFunctionType(functionNodes, type)) {
            future.complete(returnValue.get());
        }
        boolean foundFail = false;
        for (int i = 0; i < functionNodes.size(); i++) {
            FunctionNode node = functionNodes.get(i);
            FunctionToken functionToken = node.getToken();
            List<FunctionType> types = functionToken.getTypes();
            if (types.contains(type) || (type.isClick() && types.contains(FunctionType.CLICK))) {
                if (type != FunctionType.FAIL) {
                    runFunctionData(owner, functionToken.getFunctions(), playerWrapper)
                            .whenComplete((dataResponse, ex) -> {
                                if (ex != null) {
                                    ex.printStackTrace();
                                    future.complete(false);
                                } else {
                                    if (!dataResponse.result) {
                                        if (dataResponse.failedFunction == null) {
                                            future.complete(false);
                                        } else {
                                            returnValue.set(false);
                                            recurFunctionNodes(dataResponse, owner,
                                                    node.getChildren(), FunctionType.FAIL,
                                                    playerWrapper, future, returnValue);
                                        }
                                    } else {
                                        recurFunctionNodes(dataResponse, owner,
                                                node.getChildren(), type,
                                                playerWrapper, future, returnValue);
                                    }
                                }
                            });
                } else {
                    if (isFail(response, functionToken)) {
                        foundFail = true;
                        runFunctionData(owner, functionToken.getFunctions(), playerWrapper)
                                .whenComplete((dataResponse, ex) -> {
                                    if (ex != null) {
                                        ex.printStackTrace();
                                        future.complete(false);
                                    } else {
                                        recurFunctionNodes(dataResponse, owner,
                                                node.getChildren(), FunctionType.FAIL,
                                                playerWrapper, future, returnValue);
                                    }
                                });
                    } else if (i == functionNodes.size() - 1 && !foundFail) {
                        future.complete(returnValue.get());
                    }
                }
            }
        }
    }

    private boolean hasFunctionType(Collection<FunctionNode> nodes, FunctionType type) {
        for (FunctionNode node : nodes) {
            if (node.getToken().getTypes().contains(type)) {
                return true;
            }
        }
        return false;
    }

    private CompletableFuture<FunctionResponse> runFunctionData(FunctionOwner owner, List<FunctionData> functionDataList, PlayerWrapper<?> playerWrapper) {
        UUID uuid = playerWrapper.getUniqueId();
        CompletableFuture<FunctionResponse> response = new CompletableFuture<>();
        response.exceptionally((ex) -> {
            ex.printStackTrace();
            return null;
        });
        ThreadUtil.run(() -> {
            for (int i = 0; i < functionDataList.size(); i++) {
                FunctionData data = functionDataList.get(i);
                String functionName = data.getName();
                String functionData = data.getData();
                Function function = FunctionManager.get().getFunctionByName(functionName);
                if (function == null) {
                    this.logger.error("Invalid function %s", data.getName());
                    response.complete(new FunctionResponse(false));
                    return;
                }
                function.setOwner(owner);

                if (data.getData() != null) {
                    String newData = ReplacerManager.get().replace(functionData, playerWrapper);
                    function.setData(newData);
                }

                boolean async = function.isAsync();
                List<FunctionData> futureData = async ? new ArrayList<>(functionDataList.size()) : functionDataList;
                if (async) { //Load functions into new arraylist if the function is async
                    for (int j = i + 1; j < functionDataList.size(); j++) {
                        futureData.add(functionDataList.get(j));
                    }
                }
                ThreadUtil.run(() -> {
                    if (async) {
                        this.runningAsyncFunctions.compute(uuid, (key, value) -> {
                            if (value == null) {
                                return new ConcurrentHashMap<>();
                            }
                            return value;
                        }).compute(function, (key, value) -> {
                            if (value != null) {
                                value.incrementAndGet();
                            } else {
                                value = new AtomicInteger(1);
                            }
                            return value;
                        });
                    }
                    boolean ran = false;
                    try {
                        ran = function.function(playerWrapper);
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.complete(new FunctionResponse(false, functionName, functionData));
                    }
                    if (data.getModifier() == FunctionModifier.NOT) {
                        ran = !ran;
                    }
                    if (!ran) {
                        cleanupAsync(uuid, function);
                        response.complete(new FunctionResponse(false, functionName, functionData));
                    } else if (async) {
                        runFunctionData(owner, futureData, playerWrapper)
                                .whenComplete((value, ex) -> {
                                    if (ex != null) {
                                        ex.printStackTrace();
                                        response.complete(new FunctionResponse(false, value.failedFunction, value.data));
                                    } else {
                                        cleanupAsync(uuid, function);
                                        response.complete(value);
                                    }
                                });
                    }
                }, async);
                //Return if function is async since the async caller will hand
                //control back to this method after completion or the function will
                //fail and then complete
                //Also return if the future is completed, this is done because if a function is completed
                //with a sync function that failed the future will complete, but we don't want to continue
                //the loop
                if (async || response.isDone()) {
                    return;
                }
            }
            response.complete(new FunctionResponse(true));
        }, false);
        return response;
    }

    private void cleanupAsync(UUID uuid, Function function) {
        Map<Function, AtomicInteger> map = this.runningAsyncFunctions.get(uuid);
        if (map != null) {
            AtomicInteger num = map.get(function);
            if (num != null) {
                int ret = num.decrementAndGet();
                if (ret <= 0) { //Less than 0 shouldn't happen but just to ensure that the map gets cleaned up
                    map.remove(function);
                }
                if (map.size() == 0) { //If the map is empty remove
                    this.runningAsyncFunctions.remove(uuid);
                }
            }
        }
    }

    private boolean isFail(FunctionResponse response, FunctionToken token) {
        for (FunctionData data : token.getFailOnFunctions()) {
            if (data.getName().equals(response.failedFunction)) {
                if (data.getData() == null || data.getData().equals(response.data)) {
                    return true;
                }
            }
        }

        return false;
    }

    private class FunctionResponse {
        private final boolean result;
        private final String failedFunction;
        private final String data;

        public FunctionResponse(boolean result) {
            this(result, null, null);
        }

        public FunctionResponse(boolean result, String failedFunction, String data) {
            this.result = result;
            this.failedFunction = failedFunction;
            this.data = data;
        }
    }
}