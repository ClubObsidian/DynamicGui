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

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.gui.Slot;

public class CopyBackMetadataFunction extends Function {

    private static final long serialVersionUID = -4524513696416744522L;

    public CopyBackMetadataFunction() {
        super("copybackmetadata");
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        Gui gui = null;
        FunctionOwner owner = this.getOwner();
        if(owner instanceof Slot) {
            Slot slot = (Slot) owner;
            gui = slot.getOwner();
        } else if(owner instanceof Gui) {
            gui = (Gui) owner;
        }

        Gui back = gui.getBack();
        if(back == null) {
            return false;
        }
        gui.getMetadata().putAll(back.getMetadata());
        return true;
    }
}