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
package com.clubobsidian.dynamicgui.parser.function.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.clubobsidian.dynamicgui.parser.function.*;
import com.clubobsidian.dynamicgui.parser.macro.MacroParser;
import com.clubobsidian.fuzzutil.StringFuzz;
import com.clubobsidian.wrappy.ConfigurationSection;

public class FunctionTree implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3700259615018247686L;

    private final List<FunctionNode> rootNodes;
    private final MacroParser macroParser;
    private final FunctionTypeParser functionTypeParser;

    public FunctionTree() {
        this(new ArrayList<>(), new MacroParser(new ArrayList<>()));
    }

    public FunctionTree(List<FunctionNode> rootNodes, MacroParser macroParser) {
        this.rootNodes = rootNodes;
        this.macroParser = macroParser;
        this.functionTypeParser = new FunctionTypeParser(this.macroParser);
    }

    public FunctionTree(ConfigurationSection section) {
        this(section, new MacroParser(new ArrayList<>()));
    }

    public FunctionTree(ConfigurationSection section, MacroParser macroParser) {
        this(new ArrayList<>(), macroParser);
        this.parseNodes(section);
    }

    public List<FunctionNode> getRootNodes() {
        return this.rootNodes;
    }

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

            functionTokens.add(new FunctionData(functionName, functionData, modifier));
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

            FunctionToken data = new FunctionToken(name, types, functionTokens, failFunctions);
            FunctionNode childNode = new FunctionNode(depth, data);

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