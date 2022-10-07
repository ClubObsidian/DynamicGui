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

package com.clubobsidian.dynamicgui.core.test.mock.gui;

import com.clubobsidian.dynamicgui.core.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.core.gui.property.CloseableComponent;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;

public class MockCloseableFunctionOwner implements FunctionOwner, CloseableComponent {

    private Boolean close;

    @Override
    public FunctionTree getFunctions() {
        return null;
    }

    @Override
    public Boolean getClose() {
        return this.close;
    }

    @Override
    public void setClose(Boolean close) {
        this.close = close;
    }
}
