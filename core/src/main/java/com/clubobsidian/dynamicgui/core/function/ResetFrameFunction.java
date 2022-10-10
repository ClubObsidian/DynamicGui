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

package com.clubobsidian.dynamicgui.core.function;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.api.gui.Slot;

public class ResetFrameFunction extends Function {

    /**
     *
     */
    private static final long serialVersionUID = -2386244760460728686L;

    public ResetFrameFunction() {
        super("resetframe");
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        if (this.getOwner() instanceof Slot) {
            Slot slot = (Slot) this.getOwner();
            slot.resetFrame();
            return true;
        }

        return false;
    }
}
