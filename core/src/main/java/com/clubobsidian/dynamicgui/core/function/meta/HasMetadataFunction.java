/*
 *    Copyright 2018-2023 virustotalop
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

package com.clubobsidian.dynamicgui.core.function.meta;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.api.function.FunctionOwner;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.gui.MetadataHolder;
import com.clubobsidian.dynamicgui.api.gui.Slot;

public class HasMetadataFunction extends Function {


    /**
     *
     */
    private static final long serialVersionUID = -1651909249573158848L;

    public HasMetadataFunction() {
        super("hasmetadata", "getmetadata");
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        if (this.getData() == null) {
            return false;
        } else if (!this.getData().contains(",")) {
            return false;
        }

        String[] split = this.getData().split(",");
        FunctionOwner owner = this.getOwner();
        MetadataHolder holder = null;
        String first = split[0];
        Gui gui = null;

        if (owner instanceof Gui && split.length >= 2) {
            if (split.length == 2) {
                holder = (MetadataHolder) owner;
            } else {
                gui = (Gui) owner;
            }
        } else if (owner instanceof Slot && split.length >= 2) {
            if (first.equals("gui")) {
                holder = ((Slot) this.getOwner()).getOwner();
            } else if (split.length == 2) {
                holder = (MetadataHolder) this.getOwner();
            } else if (split.length == 3) {
                gui = ((Slot) owner).getOwner();
            }
        }

        //Check for slots
        if (holder == null) {
            int index = -1;
            try {
                index = Integer.valueOf(first);
            } catch (Exception ex) {
                DynamicGui.get().getLogger().error("Invalid index '%s' in HasMetadata function", first);
                return false;
            }
            for (Slot s : gui.getSlots()) {
                if (s.getIndex() == index) {
                    holder = s;
                    break;
                }
            }
        }

        if (holder != null) {
            String key = null;
            String value = null;
            if (split.length == 2) {
                key = split[0];
                value = split[1];
            } else if (split.length == 3) {
                key = split[1];
                value = split[2];
            }

            if (key != null) {
                String metaValue = holder.getMetadata().get(key);
                return value.equals(metaValue);
            }
        }

        return false;
    }
}