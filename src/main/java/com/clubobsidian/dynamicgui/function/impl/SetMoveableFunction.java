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

package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;

public class SetMoveableFunction extends Function {

    /**
     *
     */
    private static final long serialVersionUID = 453447798953153174L;

    public SetMoveableFunction(String name) {
        super(name);
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        if(this.getOwner() instanceof Slot) {
            Boolean value = Boolean.valueOf(this.getData());
            if(value != null) {
                FunctionOwner owner = this.getOwner();
                if(owner != null) {
                    if(owner instanceof Slot) {
                        Slot slot = (Slot) owner;
                        Gui gui = slot.getOwner();
                        if(gui != null) {
                            slot.setMoveable(value);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}