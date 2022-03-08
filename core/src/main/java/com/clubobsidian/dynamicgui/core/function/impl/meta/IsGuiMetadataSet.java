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

package com.clubobsidian.dynamicgui.core.function.impl.meta;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.gui.Slot;

public class IsGuiMetadataSet extends Function {

    private static final long serialVersionUID = -5704765632825840069L;

    public IsGuiMetadataSet() {
        super("IsGuiMetadataSet");
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        Gui gui;
        if (this.getOwner() instanceof Gui) {
            gui = (Gui) this.getOwner();
        } else if (this.getOwner() instanceof Slot) {
            gui = ((Slot) this.getOwner()).getOwner();
        } else {
            String clazzName = this.getOwner().getClass().getName();
            DynamicGui.get().getLogger().error("Invalid function owner type: " + clazzName);
            return false;
        }
        return gui.getMetadata().get(this.getData()) != null;
    }
}
