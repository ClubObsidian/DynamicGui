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
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckItemTypeInHandFunction extends Function {

    /**
     *
     */
    private static final long serialVersionUID = -2308186311331769892L;

    public CheckItemTypeInHandFunction(String name) {
        super(name);
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        List<String> types = new ArrayList<>();
        types.add(this.getData());

        if(this.getData().contains(",")) {
            types = Arrays.asList(this.getData().split(","));
        }

        //Uppercase
        for(int i = 0; i < types.size(); i++) {
            types.set(i, types.get(i).toUpperCase());
        }

        ItemStackWrapper<?> wrapper = playerWrapper.getItemInHand();
        String type = wrapper.getType().toUpperCase();
        return types.contains(type);
    }
}