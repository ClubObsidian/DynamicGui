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

import com.clubobsidian.dynamicgui.api.factory.FunctionTokenFactory;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface FunctionToken extends Serializable {

    String getName();

    List<FunctionType> getTypes();

    List<FunctionData> getFunctions();

    List<FunctionData> getFailOnFunctions();

    final class Builder {

        @Inject
        private static FunctionTokenFactory FACTORY;

        private final String name = UUID.randomUUID().toString();
        private final List<FunctionType> types = new ArrayList<>();
        private final List<FunctionData> functions = new ArrayList<>();
        private final List<FunctionData> failOnFunctions = new ArrayList<>();

        public Builder addType(String type) {
            FunctionType functionType = FunctionType.valueOf(type.toUpperCase());
            this.types.add(functionType);
            return this;
        }

        public Builder addType(String... types) {
            for (String t : types) {
                this.addType(t);
            }
            return this;
        }

        public Builder addFunction(String name, String data) {
            return this.addFunction(name, data, FunctionModifier.NONE);
        }

        public Builder addFunction(String name, String data, FunctionModifier modifier) {
            return this.addFunction(new FunctionData.Builder()
                    .setName(name)
                    .setData(data)
                    .setModifier(modifier)
                    .build());
        }

        public Builder addFunction(FunctionData data) {
            this.functions.add(data);
            return this;
        }

        public Builder addFunction(FunctionData... datas) {
            for (FunctionData data : datas) {
                this.addFunction(data);
            }

            return this;
        }

        public Builder addFailOnFunction(String name, String data) {
            return this.addFailOnFunction(name, data, FunctionModifier.NONE);
        }

        public Builder addFailOnFunction(String name, String data, FunctionModifier modifier) {
            return this.addFailOnFunction(new FunctionData.Builder()
                    .setName(name)
                    .setData(data)
                    .setModifier(modifier)
                    .build());
        }

        public Builder addFailOnFunction(FunctionData data) {
            this.failOnFunctions.add(data);
            return this;
        }

        public Builder addFailOnFunction(FunctionData... datas) {
            for (FunctionData data : datas) {
                this.addFunction(data);
            }
            return this;
        }

        public FunctionToken build() {
            return FACTORY.create(this.name, this.types, this.functions, this.failOnFunctions);
        }
    }
}