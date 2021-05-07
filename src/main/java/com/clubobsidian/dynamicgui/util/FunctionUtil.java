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
package com.clubobsidian.dynamicgui.util;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.manager.dynamicgui.FunctionManager;
import com.clubobsidian.dynamicgui.manager.dynamicgui.ReplacerManager;
import com.clubobsidian.dynamicgui.parser.function.FunctionData;
import com.clubobsidian.dynamicgui.parser.function.FunctionModifier;
import com.clubobsidian.dynamicgui.parser.function.FunctionToken;
import com.clubobsidian.dynamicgui.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionNode;

import java.util.List;

public final class FunctionUtil {

    private FunctionUtil() {
    }

    public static boolean tryFunctions(FunctionOwner owner, FunctionType type, PlayerWrapper<?> playerWrapper) {
        return recurFunctionNodes(null, owner, owner.getFunctions().getRootNodes(), type, playerWrapper);
    }

    private static boolean recurFunctionNodes(FunctionResponse fail, FunctionOwner owner, List<FunctionNode> functionNodes, FunctionType type, PlayerWrapper<?> playerWrapper) {
        for(FunctionNode node : functionNodes) {
            FunctionToken functionToken = node.getToken();
            List<FunctionType> types = functionToken.getTypes();
            if(types.contains(type) || (type.isClick() && types.contains(FunctionType.CLICK))) {
                if(type != FunctionType.FAIL) {
                    FunctionResponse response = runFunctionData(owner, functionToken.getFunctions(), playerWrapper);

                    if(!response.result) {
                        if(response.failedFunction == null) {
                            return false;
                        }

                        recurFunctionNodes(response, owner, node.getChildren(), FunctionType.FAIL, playerWrapper);
                        return false;
                    } else {
                        recurFunctionNodes(response, owner, node.getChildren(), type, playerWrapper);
                    }
                } else if(type == FunctionType.FAIL) {
                    if(isFail(fail, functionToken)) {
                        FunctionResponse response = runFunctionData(owner, functionToken.getFunctions(), playerWrapper);
                        if(!response.result) {
                            recurFunctionNodes(response, owner, node.getChildren(), FunctionType.FAIL, playerWrapper);
                        }
                    }
                }
            }
        }

        return true;
    }

    private static FunctionResponse runFunctionData(FunctionOwner owner, List<FunctionData> datas, PlayerWrapper<?> playerWrapper) {
        for(FunctionData data : datas) {
            String functionName = data.getName();
            String functionData = data.getData();
            Function function = FunctionManager.get().getFunctionByName(functionName);
            if(function == null) {
                DynamicGui.get().getLogger().error("Invalid function " + data.getName());
                return new FunctionResponse(false);
            }

            function.setOwner(owner);

            if(data.getData() != null) {
                String newData = ReplacerManager.get().replace(functionData, playerWrapper);
                function.setData(newData);
            }

            boolean ran = function.function(playerWrapper);
            if(data.getModifier() == FunctionModifier.NOT) {
                ran = !ran;
            }

            if(!ran) {
                return new FunctionResponse(false, functionName, functionData);
            }
        }
        return new FunctionResponse(true);
    }

    private static boolean isFail(FunctionResponse response, FunctionToken token) {
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

    private static class FunctionResponse {
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