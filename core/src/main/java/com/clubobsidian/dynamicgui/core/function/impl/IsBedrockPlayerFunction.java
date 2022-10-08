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

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;

public class IsBedrockPlayerFunction extends Function {


    private static final long serialVersionUID = -2186429958998223776L;

    public IsBedrockPlayerFunction() {
        super("isbedrockplayer");
    }

    @Override
    public boolean function(final PlayerWrapper<?> playerWrapper) {
        return playerWrapper.getUniqueId().getMostSignificantBits() == 0L;
    }
}