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

import com.clubobsidian.dynamicgui.api.enchantment.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.function.FunctionOwner;
import com.clubobsidian.dynamicgui.api.gui.animation.AnimationHolder;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface Slot extends Serializable, FunctionOwner, AnimationHolder, MetadataHolder, CloseableComponent {


    String IGNORE_MATERIAL = "AIR";
    String TEST_MATERIAL = "STONE";

    int getIndex();

    void setIndex(int index);

    String getIcon();

    int getAmount();

    String getName();

    String getNBT();

    boolean getGlow();

    boolean isMovable();

    void setMovable(boolean movable);

    short getData();

    List<String> getLore();

    List<EnchantmentWrapper> getEnchants();

    List<String> getItemFlags();

    Boolean getClose();

    void setClose(Boolean close);

    FunctionTree getFunctions();

    ItemStackWrapper<?> buildItemStack(PlayerWrapper<?> playerWrapper);

    ItemStackWrapper<?> getItemStack();

    void setOwner(Gui gui);

    Gui getOwner();

    @Override
    int getUpdateInterval();

    int getCurrentTick();

    void resetTick();

    int tick();

    int getFrame();

    void resetFrame();

    Map<String, String> getMetadata();

    boolean getUpdate();

    void setUpdate(boolean update);
}