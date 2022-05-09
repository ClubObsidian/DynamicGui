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

package com.clubobsidian.dynamicgui.core.function.impl;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;

public class CheckLevelFunction extends Function {

    /**
     *
     */
    private static final long serialVersionUID = 4927665292013293816L;

    public CheckLevelFunction() {
        super("checklevel");
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        if (this.getData() == null) {
            return false;
        }
        try {
            int level = Integer.parseInt(this.getData());
            return playerWrapper.getLevel() >= level;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}