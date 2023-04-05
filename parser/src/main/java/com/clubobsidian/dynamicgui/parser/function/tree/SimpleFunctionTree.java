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

package com.clubobsidian.dynamicgui.parser.function.tree;

import com.clubobsidian.dynamicgui.api.parser.function.FunctionData;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionModifier;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionToken;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionTypeParser;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionNode;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.api.parser.macro.MacroParser;
import com.clubobsidian.dynamicgui.parser.function.SimpleFunctionData;
import com.clubobsidian.dynamicgui.parser.function.SimpleFunctionToken;
import com.clubobsidian.dynamicgui.parser.function.SimpleFunctionTypeParser;
import com.clubobsidian.dynamicgui.parser.macro.SimpleMacroParser;
import com.clubobsidian.fuzzutil.StringFuzz;
import com.clubobsidian.wrappy.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class SimpleFunctionTree implements FunctionTree {

    /**
     *
     */
    private static final long serialVersionUID = -3700259615018247686L;

    private final List<FunctionNode> rootNodes;
    private final MacroParser macroParser;
    private final FunctionTypeParser functionTypeParser;

    public SimpleFunctionTree() {
        this(new ArrayList<>(), new SimpleMacroParser(new ArrayList<>()));
    }

    public SimpleFunctionTree(List<FunctionNode> rootNodes, MacroParser macroParser) {
        this.rootNodes = rootNodes;
        this.macroParser = macroParser;
        this.functionTypeParser = new SimpleFunctionTypeParser(this.macroParser);
    }

    public SimpleFunctionTree(ConfigurationSection section) {
        this(section, new SimpleMacroParser(new ArrayList<>()));
    }

    public SimpleFunctionTree(ConfigurationSection section, MacroParser macroParser) {
        this(new ArrayList<>(), macroParser);
        this.parseNodes(section);
    }

    @Override
    public List<FunctionNode> getRootNodes() {
        return this.rootNodes;
    }

    @Override
    public MacroParser getMacroParser() {
        return this.macroParser;
    }

    private void parseNodes(ConfigurationSection section) {
        int depth = 0;
        this.walkTree(depth, section, null);
    }

    private String[] parseFunctionData(String functionData) {
        String[] args = new String[3];
        if (!functionData.contains(":")) {
            args[0] = StringFuzz.normalize(functionData);
            FunctionModifier modifier = FunctionModifier.findModifier(args[0]);
            args[0] = functionData.replaceFirst(modifier.getModifier(), "");
            args[1] = null;
            args[2] = modifier.name();
            return args;
        }

        String[] split = functionData.split(":");
        StringBuilder dat = new StringBuilder();
        dat.append(split[1].trim());

        if (split.length > 2) {
            for (int i = 2; i < split.length; i++) {
                dat.append(":");
                dat.append(split[i]);
            }
        }

        args[0] = StringFuzz.normalize(split[0]);
        args[1] = dat.toString();
        FunctionModifier modifier = FunctionModifier.findModifier(args[0]);
        args[0] = args[0].replaceFirst(modifier.getModifier(), "");
        args[2] = modifier.name();
        return args;
    }

    private List<FunctionData> parseFunctionData(final List<String> tokens) {
        List<String> parsedTokens = this.macroParser.parseListMacros(tokens);

        List<FunctionData> functionTokens = new ArrayList<>();
        for (String token : parsedTokens) {
            String[] parsedFunctionData = this.parseFunctionData(token);

            String functionName = parsedFunctionData[0];
            String functionData = parsedFunctionData[1];
            String modifierStr = parsedFunctionData[2];
            FunctionModifier modifier = FunctionModifier.valueOf(modifierStr);

            functionTokens.add(new SimpleFunctionData(functionName, functionData, modifier));
        }
        return functionTokens;
    }

    private void walkTree(int depth, ConfigurationSection section, FunctionNode parentNode) {
        for (String name : section.getKeys()) {
            ConfigurationSection rootSection = section.getConfigurationSection(name);
            if (rootSection.get("functions") == null) {
                continue;
            }

            List<FunctionType> types = this.functionTypeParser.parseTypes(rootSection.getStringList("type"));
            List<FunctionData> functionTokens = this.parseFunctionData(rootSection.getStringList("functions"));
            List<FunctionData> failFunctions = this.parseFunctionData(rootSection.getStringList("fail-on"));

            FunctionToken data = new SimpleFunctionToken(name, types, functionTokens, failFunctions);
            FunctionNode childNode = new SimpleFunctionNode(name, depth, data);

            if (depth == 0) {
                this.rootNodes.add(childNode);
            } else {
                parentNode.addChild(childNode);
            }

            int newDepth = depth + 1;
            walkTree(newDepth, rootSection, childNode);
        }
    }
}