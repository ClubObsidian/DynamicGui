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
package com.clubobsidian.dynamicgui.core.manager.dynamicgui;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.core.util.ThreadUtil;
import com.clubobsidian.dynamicgui.parser.function.FunctionData;
import com.clubobsidian.dynamicgui.parser.function.FunctionModifier;
import com.clubobsidian.dynamicgui.parser.function.FunctionToken;
import com.clubobsidian.dynamicgui.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionNode;
import com.clubobsidian.fuzzutil.StringFuzz;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class FunctionManager {

    private static FunctionManager instance;

    private final Map<String, Function> functions;

    private FunctionManager() {
        this.functions = new HashMap<>();
    }

    public static FunctionManager get() {
        if(instance == null) {
            instance = new FunctionManager();
        }
        return instance;
    }

    public Function getFunctionByName(String functionName) {
        String normalized = StringFuzz.normalize(functionName);
        Function function = this.functions.get(normalized);
        if(function == null) {
            return null;
        }

        return function.clone();
    }

    public List<Function> getFunctions() {
        return new ArrayList<>(this.functions.values());
    }

    public boolean addFunction(Function function) {
        boolean wasNotNull = this.functions.put(function.getName(), function) != null;
        for(String alias : function.getAliases()) {
            if(this.functions.put(alias, function) != null) {
                wasNotNull = true;
            }
        }
        return wasNotNull;
    }

    public boolean removeFunctionByName(String functionName) {
        String normalized = StringFuzz.normalize(functionName);
        return this.functions.keySet().remove(normalized);
    }

    public CompletableFuture<Boolean> tryFunctions(FunctionOwner owner, FunctionType type, PlayerWrapper<?> playerWrapper) {
        return recurFunctionNodes(null, owner, owner.getFunctions().getRootNodes(), type,
                playerWrapper, null, new AtomicBoolean(true));
    }

    private CompletableFuture<Boolean> recurFunctionNodes(FunctionResponse response,
                                                          FunctionOwner owner,
                                                          Collection<FunctionNode> functionNodes,
                                                          FunctionType type,
                                                          PlayerWrapper<?> playerWrapper,
                                                          CompletableFuture<Boolean> incomingFuture,
                                                          AtomicBoolean returnValue) {
        Queue<FunctionNode> nodeQueue = new ArrayDeque<>(functionNodes);
        CompletableFuture<Boolean> future = incomingFuture == null ? new CompletableFuture<>() : incomingFuture;
        future.exceptionally((ex) -> {
            ex.printStackTrace();
            return null;
        });
        FunctionNode node = nodeQueue.poll();
        if(node != null) {
            FunctionToken functionToken = node.getToken();
            List<FunctionType> types = functionToken.getTypes();
            if(types.contains(type) || (type.isClick() && types.contains(FunctionType.CLICK))) {
                if(type != FunctionType.FAIL) {
                    runFunctionData(owner, functionToken.getFunctions(), playerWrapper).thenAccept(dataResponse -> {
                        if(!dataResponse.result) {
                            if(dataResponse.failedFunction == null) {
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
                    });
                } else if(type == FunctionType.FAIL) {
                    if(isFail(response, functionToken)) {
                        runFunctionData(owner, functionToken.getFunctions(), playerWrapper).thenAccept(dataResponse -> {
                            recurFunctionNodes(dataResponse, owner,
                                    node.getChildren(), FunctionType.FAIL,
                                    playerWrapper, future, returnValue);
                        });
                    }
                }
            }
        }
        if(node == null) {
            future.complete(returnValue.get());
        }
        return future;
    }

    private CompletableFuture<FunctionResponse> runFunctionData(FunctionOwner owner, List<FunctionData> functionDataList, PlayerWrapper<?> playerWrapper) {
        CompletableFuture<FunctionResponse> response = new CompletableFuture<>();
        response.exceptionally((ex) -> {
            ex.printStackTrace();
            return null;
        });
        ThreadUtil.run(() -> {
            for(int i = 0; i < functionDataList.size(); i++) {
                FunctionData data = functionDataList.get(i);
                String functionName = data.getName();
                String functionData = data.getData();
                Function function = FunctionManager.get().getFunctionByName(functionName);
                if(function == null) {
                    DynamicGui.get().getLogger().error("Invalid function " + data.getName());
                    response.complete(new FunctionResponse(false));
                    return;
                }
                function.setOwner(owner);

                if(data.getData() != null) {
                    String newData = ReplacerManager.get().replace(functionData, playerWrapper);
                    function.setData(newData);
                }

                boolean async = function.isAsync();
                List<FunctionData> futureData = async ? new ArrayList<>(functionDataList.size()) : functionDataList;
                if(async) { //Load functions into new arraylist if the function is async
                    for(int j = i + 1; j < functionDataList.size(); j++) {
                        futureData.add(functionDataList.get(j));
                    }
                }
                ThreadUtil.run(() -> {
                    boolean ran = function.function(playerWrapper);
                    if(data.getModifier() == FunctionModifier.NOT) {
                        ran = !ran;
                    }
                    if(!ran) {
                        response.complete(new FunctionResponse(false, functionName, functionData));
                    } else if(async) {
                        try {
                            response.complete(runFunctionData(owner, futureData, playerWrapper).get());
                        } catch(InterruptedException | ExecutionException ex) {
                            ex.printStackTrace();
                        }
                    }
                }, async);
                //Return if function is async since the async caller will hand
                //control back to this method after completion or the function will
                //fail and then complete
                //Also return if the future is completed, this is done because if a function is completed
                //with a sync function that failed the future will complete, but we don't want to continue
                //the loop
                if(async || response.isDone()) {
                    return;
                }
            }
            response.complete(new FunctionResponse(true));
        }, false);
        return response;
    }

    private boolean isFail(FunctionResponse response, FunctionToken token) {
        for(FunctionData data : token.getFailOnFunctions()) {
            if(data.getName().equals(response.failedFunction)) {
                if(data.getData() == null) {
                    return true;
                } else if(data.getData().equals(response.data)) {
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