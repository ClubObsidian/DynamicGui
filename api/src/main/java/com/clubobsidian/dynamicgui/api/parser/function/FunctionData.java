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

package com.clubobsidian.dynamicgui.api.parser.function;

import com.clubobsidian.dynamicgui.api.factory.FunctionDataFactory;
import com.clubobsidian.fuzzutil.StringFuzz;

import javax.inject.Inject;
import java.io.Serializable;

public interface FunctionData extends Serializable {


    String getName();

    String getData();

    FunctionModifier getModifier();

    class Builder {

        @Inject
        private static FunctionDataFactory FACTORY;

        private transient String name;
        private transient String data;
        private transient FunctionModifier modifier = FunctionModifier.NONE;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setData(String data) {
            this.data = data;
            return this;
        }

        public Builder setModifier(FunctionModifier modifier) {
            this.modifier = modifier;
            return this;
        }

        public FunctionData build() {
            return FACTORY.create(this.name, this.data, this.modifier);
        }
    }
}