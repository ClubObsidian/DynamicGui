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

import com.clubobsidian.dynamicgui.parser.function.FunctionToken;

public class FunctionNode implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8331607544830166913L;

    private final int depth;
    private final FunctionToken token;
    private final List<FunctionNode> children;

    public FunctionNode(int depth, FunctionToken token) {
        this.depth = depth;
        this.token = token;
        this.children = new ArrayList<>();
    }

    public int getDepth() {
        return this.depth;
    }

    public FunctionToken getToken() {
        return this.token;
    }

    public List<FunctionNode> getChildren() {
        return this.children;
    }

    public boolean addChild(FunctionNode child) {
        return this.children.add(child);
    }
}