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
import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.api.function.FunctionOwner;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.gui.Slot;

import java.util.ArrayList;
import java.util.List;

public class RefreshSlotFunction extends Function {

    /**
     *
     */
    private static final long serialVersionUID = 1079816229207205846L;

    public RefreshSlotFunction() {
        super("refreshslot");
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        if (this.getData() == null) {
            if (this.getOwner() instanceof Gui) {
                return false;
            }

            Slot slot = (Slot) this.getOwner();
            slot.setUpdate(true);
            return true;
        }

        try {
            String data = this.getData();
            List<Integer> slotIndexs = new ArrayList<>();
            if (!data.contains(",")) {
                int parsed = Integer.parseInt(data);
                slotIndexs.add(parsed);
            } else {
                String[] split = data.split(",");
                for (String str : split) {
                    Integer parsed = Integer.parseInt(str);
                    slotIndexs.add(parsed);
                }
            }

            Gui gui = null;
            FunctionOwner owner = this.getOwner();
            if (owner instanceof Slot) {
                Slot slot = (Slot) owner;
                gui = slot.getOwner();
            } else if (owner instanceof Gui) {
                gui = (Gui) owner;
            }

            List<Slot> slots = gui.getSlots();
            for (Slot slot : slots) {
                if (slotIndexs.contains(slot.getIndex())) {
                    slot.setUpdate(true);
                }
            }

            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}