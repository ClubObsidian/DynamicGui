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

package com.clubobsidian.dynamicgui.test.mock;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

import java.util.UUID;

public abstract class MockPlayerWrapper extends PlayerWrapper<MockPlayer> {

    private ItemStackWrapper<?> hand;

    public MockPlayerWrapper() {
        this(new MockPlayer());
    }

    public MockPlayerWrapper(MockPlayer player) {
        super(player);
    }

    @Override
    public String getName() {
        return this.getPlayer().getName();
    }

    @Override
    public UUID getUniqueId() {
        return this.getPlayer().getUniqueId();
    }

    @Override
    public void chat(String message) {
        this.getPlayer().chat(message);
    }

    @Override
    public void sendMessage(String message) {
        this.getPlayer().sendMessage(message);
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.getPlayer().hasPermission(permission);
    }

    @Override
    public boolean addPermission(String permission) {
        return this.getPlayer().addPermission(permission);
    }

    @Override
    public ItemStackWrapper<?> getItemInHand() {
        return this.getPlayer().getItemInHand();
    }

    public void setItemInHand(ItemStackWrapper<?> hand) {
        this.getPlayer().setItemInHand(hand);
    }

    @Override
    public int getLevel() {
        return this.getPlayer().getLevel();
    }

    public void setLevel(int level) {
        this.getPlayer().setLevel(level);
    }
}