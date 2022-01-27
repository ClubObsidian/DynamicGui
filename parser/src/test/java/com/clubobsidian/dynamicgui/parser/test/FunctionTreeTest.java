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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.clubobsidian.dynamicgui.parser.function.FunctionData;
import com.clubobsidian.dynamicgui.parser.function.FunctionToken;
import com.clubobsidian.dynamicgui.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionNode;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.parser.macro.MacroToken;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;

public class FunctionTreeTest {

    private static FunctionTree tree;

    @BeforeClass
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
        assertTrue("Root node size is not three", rootNodeSize == 3);
    }

    @Test
    public void testFunctionTypes() {
        FunctionNode node = tree.getRootNodes().get(0);
        FunctionToken token = node.getToken();
        FunctionType type = token.getTypes().get(0);
        assertTrue("Function type is not load", type == FunctionType.LOAD);
    }

    @Test
    public void testDescend() {
        List<FunctionNode> childrenNodes = tree.getRootNodes().get(0).getChildren();
        int childrenNodeSize = childrenNodes.size();
        FunctionToken token = childrenNodes.get(0).getToken();
        FunctionData data = token.getFunctions().get(0);
        assertTrue("Children node size for descend is not one", childrenNodeSize == 1);
        assertTrue("Function is not function", data.getName().equals("function"));
        assertTrue("Function data for child not is not 'with other data'", data.getData().equals("with other data"));
    }

    @Test
    public void testDescendTwoFunctions() {
        List<FunctionNode> childrenNodes = tree.getRootNodes().get(1).getChildren();
        int childrenNodeSize = childrenNodes.size();
        assertTrue("Children node size for descend is not two", childrenNodeSize == 2);
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
        assertTrue("Depth is not two", depth == 2);
        assertTrue("Children node size for depth two is not one", childrenNodeSize == 1);
        assertTrue("Invalid data for depth-2-left node", data.getData().equals("some other data"));
        assertTrue("Section name is not 'depth-2-left'", name.equals("depth-2-left"));
    }

    @Test
    public void testColonParsing() {
        FunctionNode node = tree.getRootNodes().get(2);
        FunctionToken token = node.getToken();
        FunctionData data = token.getFunctions().get(1);
        String functionData = data.getData();
        assertTrue("Function data is not 'with:a colon'", functionData.equals("with:a colon"));
    }

    @Test
    public void testTrimming() {
        FunctionNode node = tree.getRootNodes().get(2);
        FunctionToken token = node.getToken();
        FunctionData data = token.getFunctions().get(2);
        String functionDataStr = data.getData();
        assertTrue("Function data is not 'test trimming'", functionDataStr.equals("test trimming"));
    }

    @Test
    public void testNormalize() {
        FunctionNode node = tree.getRootNodes().get(0);
        FunctionToken token = node.getToken();
        FunctionData data = token.getFunctions().get(1);
        String functionName = data.getName();
        assertTrue("Function normalization failed, function is not 'functiontonormalize'", functionName.equals("functiontonormalize"));
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

        assertTrue("Function normalization failed, function is not 'onfailfunction'", functionName.equals("onfailfunction"));
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

        assertTrue("Function normalization failed, function is not 'onfailfunctionstring'", functionName.equals("onfailfunctionstring"));
    }

    @Test
    public void testMacroTokens() {
        List<MacroToken> tokens = tree.getMacroParser().getTokens();
        assertTrue("MacroToken's for functions should not be null", tokens != null);
    }
}