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

package com.clubobsidian.dynamicgui.core.function.gui;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.function.FunctionOwner;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.manager.GuiManager;
import com.clubobsidian.dynamicgui.api.function.Function;

import java.util.concurrent.ExecutionException;

public class BackFunction extends Function {

    /**
     *
     */
    private static final long serialVersionUID = 7851730396417693718L;

    public BackFunction() {
        super("back", true);
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        Gui gui = null;
        FunctionOwner owner = this.getOwner();
        if (owner instanceof Slot) {
            Slot slot = (Slot) owner;
            gui = slot.getOwner();
            slot.setClose(false);
        } else if (owner instanceof Gui) {
            gui = (Gui) owner;
        }

        Gui back = gui.getBack();
        if (back != null) {
            if (this.getData() != null) {
                try {
                    int backAmount = Integer.parseInt(this.getData());
                    for (int i = 1; i < backAmount; i++) {
                        Gui nextBack = back.getBack();
                        if (nextBack != null) {
                            back = nextBack;
                        }
                    }
                } catch (NumberFormatException ex) {
                    return false;
                }
            }
            try {
                return GuiManager.get().openGui(playerWrapper, back).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}