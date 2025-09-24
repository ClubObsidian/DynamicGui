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

package com.clubobsidian.dynamicgui.api.parser.function.tree;

import com.clubobsidian.dynamicgui.api.factory.FunctionNodeFactory;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionToken;

import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface FunctionNode extends Serializable {

    String getName();

    int getDepth();

    FunctionToken getToken();

    List<FunctionNode> getChildren();

    boolean addChild(FunctionNode child);

    final class Builder {

        @Inject
        private static FunctionNodeFactory FACTORY;

        private final String name = UUID.randomUUID().toString();
        private int depth = 0;
        private FunctionToken token;

        public Builder setDepth(int depth) {
            this.depth = depth;
            return this;
        }

        public Builder setToken(FunctionToken token) {
            this.token = token;
            return this;
        }

        public FunctionNode build() {
            return FACTORY.create(this.name, this.depth, this.token);
        }
    }
}