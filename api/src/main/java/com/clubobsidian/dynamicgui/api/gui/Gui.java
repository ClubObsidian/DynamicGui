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

package com.clubobsidian.dynamicgui.api.gui;

import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface Gui extends Serializable, FunctionOwner, MetadataHolder, CloseableComponent {


    InventoryWrapper<?> buildInventory(PlayerWrapper<?> playerWrapper);

    String getName();

    String getType();

    String getTitle();

    int getRows();

    List<Slot> getSlots();

    Boolean getClose();

    void setClose(Boolean close);

    Map<String, List<Integer>> getNpcIds();

    List<LocationWrapper<?>> getLocations();

    ModeEnum getModeEnum();

    InventoryWrapper<?> getInventoryWrapper();

    FunctionTree getFunctions();

    Map<String, String> getMetadata();


    Gui getBack();

    void setBack(Gui back);

    boolean isStatic();

    Gui clone();
}