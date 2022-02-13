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
package com.clubobsidian.dynamicgui.parser.test;

import com.clubobsidian.dynamicgui.parser.function.FunctionData;
import com.clubobsidian.dynamicgui.parser.function.FunctionToken;
import com.clubobsidian.dynamicgui.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionNode;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.parser.macro.MacroToken;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FunctionTreeTest {

    private static FunctionTree tree;

    @BeforeAll
    public static void loadTree() {
        File testFile = new File("test.yml");
        System.out.println(testFile.getAbsolutePath());
        Configuration config = Configuration.load(testFile);
        ConfigurationSection firstSlotSection = config.getConfigurationSection("0");
        ConfigurationSection functionSection = firstSlotSection.getConfigurationSection("functions");
        tree = new FunctionTree(functionSection);
    }

    @Test
    public void testRootNodeSize() {
        int rootNodeSize = tree.getRootNodes().size();
        assertTrue(rootNodeSize == 3);
    }

    @Test
    public void testFunctionTypes() {
        FunctionNode node = tree.getRootNodes().get(0);
        FunctionToken token = node.getToken();
        FunctionType type = token.getTypes().get(0);
        assertTrue(type == FunctionType.LOAD);
    }

    @Test
    public void testDescend() {
        List<FunctionNode> childrenNodes = tree.getRootNodes().get(0).getChildren();
        int childrenNodeSize = childrenNodes.size();
        FunctionToken token = childrenNodes.get(0).getToken();
        FunctionData data = token.getFunctions().get(0);
        assertTrue(childrenNodeSize == 1);
        assertTrue(data.getName().equals("function"));
        assertTrue(data.getData().equals("with other data"));
    }

    @Test
    public void testDescendTwoFunctions() {
        List<FunctionNode> childrenNodes = tree.getRootNodes().get(1).getChildren();
        int childrenNodeSize = childrenNodes.size();
        assertTrue(childrenNodeSize == 2);
    }

    @Test
    public void testDepthTwo() {
        List<FunctionNode> childrenNodes = tree.getRootNodes().get(1).getChildren().get(0).getChildren();
        int childrenNodeSize = childrenNodes.size();
        FunctionNode node = childrenNodes.get(0);
        FunctionToken token = node.getToken();
        String name = node.getToken().getName();
        int depth = node.getDepth();
        FunctionData data = token.getFunctions().get(0);
        assertTrue(depth == 2);
        assertTrue(childrenNodeSize == 1);
        assertTrue(data.getData().equals("some other data"));
        assertTrue(name.equals("depth-2-left"));
    }

    @Test
    public void testColonParsing() {
        FunctionNode node = tree.getRootNodes().get(2);
        FunctionToken token = node.getToken();
        FunctionData data = token.getFunctions().get(1);
        String functionData = data.getData();
        assertTrue(functionData.equals("with:a colon"));
    }

    @Test
    public void testTrimming() {
        FunctionNode node = tree.getRootNodes().get(2);
        FunctionToken token = node.getToken();
        FunctionData data = token.getFunctions().get(2);
        String functionDataStr = data.getData();
        assertTrue(functionDataStr.equals("test trimming"));
    }

    @Test
    public void testNormalize() {
        FunctionNode node = tree.getRootNodes().get(0);
        FunctionToken token = node.getToken();
        FunctionData data = token.getFunctions().get(1);
        String functionName = data.getName();
        assertTrue(functionName.equals("functiontonormalize"));
    }

    @Test
    public void testFailFunctionsList() {
        File testFile = new File("test.yml");
        Configuration config = Configuration.load(testFile);
        ConfigurationSection firstSlotSection = config.getConfigurationSection("4");
        ConfigurationSection functionSection = firstSlotSection.getConfigurationSection("functions");
        FunctionTree tree = new FunctionTree(functionSection);

        FunctionNode node = tree.getRootNodes().get(1);
        FunctionToken token = node.getToken();
        FunctionData data = token.getFailOnFunctions().get(0);
        String functionName = data.getName();

        assertTrue(functionName.equals("onfailfunction"));
    }

    @Test
    public void testFailFunctionsString() {
        File testFile = new File("test.yml");
        Configuration config = Configuration.load(testFile);
        ConfigurationSection firstSlotSection = config.getConfigurationSection("4");
        ConfigurationSection functionSection = firstSlotSection.getConfigurationSection("functions");
        FunctionTree tree = new FunctionTree(functionSection);

        FunctionNode node = tree.getRootNodes().get(2);
        FunctionToken token = node.getToken();
        FunctionData data = token.getFailOnFunctions().get(0);
        String functionName = data.getName();

        assertTrue(functionName.equals("onfailfunctionstring"));
    }

    @Test
    public void testMacroTokens() {
        List<MacroToken> tokens = tree.getMacroParser().getTokens();
        assertTrue(tokens != null);
    }
}