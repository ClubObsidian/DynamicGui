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

package com.clubobsidian.dynamicgui.core.test.mock.entity.player;

import com.clubobsidian.dynamicgui.api.effect.ParticleWrapper;
import com.clubobsidian.dynamicgui.api.effect.SoundWrapper;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.world.LocationWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
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
        return this.getNative().getName();
    }

    @Override
    public UUID getUniqueId() {
        return this.getNative().getUniqueId();
    }

    @Override
    public void chat(@NotNull String message) {
        this.getNative().chat(message);
    }

    public List<String> getOutgoingChat() {
        return this.getNative().getOutgoingChat();
    }

    public List<String> getIncomingChat() {
        return this.getNative().getIncomingChat();
    }

    @Override
    public void sendMessage(@NotNull String message) {
        this.getNative().sendMessage(message);
    }

    @Override
    public void sendJsonMessage(@NotNull String json) {
        this.sendMessage(json);
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return this.getNative().hasPermission(permission);
    }

    @Override
    public boolean addPermission(@NotNull String permission) {
        return this.getNative().addPermission(permission);
    }

    @Override
    public boolean removePermission(@NotNull String permission) {
        return this.getNative().removePermission(permission);
    }

    @Override
    public ItemStackWrapper<?> getItemInHand() {
        return this.getNative().getItemInHand();
    }

    public void setItemInHand(ItemStackWrapper<?> hand) {
        this.getNative().setItemInHand(hand);
    }

    @Override
    public int getLevel() {
        return this.getNative().getLevel();
    }

    public void setLevel(int level) {
        this.getNative().setLevel(level);
    }

    @Override
    public LocationWrapper<?> getLocation() {
        return this.getNative().getLocation();
    }

    public void setLocation(LocationWrapper<?> location) {
        this.getNative().setLocation(location);
    }

    @Override
    public void playEffect(ParticleWrapper.@NotNull ParticleData particleData) {
        this.getNative().playEffect(particleData);
    }

    public List<ParticleWrapper.ParticleData> getParticles() {
        return this.getNative().getParticles();
    }

    @Override
    public void playSound(SoundWrapper.@NotNull SoundData soundData) {
        this.getNative().playSound(soundData);
    }

    public List<SoundWrapper.SoundData> getSounds() {
        return this.getNative().getSounds();
    }

    @Override
    public boolean isOnline() {
        return this.getNative().isOnline();
    }

    public void setOnline(boolean online) {
        this.getNative().setOnline(online);
    }
}