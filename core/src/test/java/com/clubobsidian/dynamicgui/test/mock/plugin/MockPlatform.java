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

package com.clubobsidian.dynamicgui.test.mock.plugin;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.messaging.MessagingRunnable;
import com.clubobsidian.dynamicgui.core.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.core.scheduler.Scheduler;
import com.clubobsidian.dynamicgui.core.server.Platform;
import com.clubobsidian.dynamicgui.core.server.ServerType;
import com.clubobsidian.dynamicgui.core.world.WorldWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MockPlatform extends Platform {

    private final Map<String, WorldWrapper<?>> worlds = new HashMap<>();
    private final List<String> dispatchedServerCommands = new ArrayList<>();

    public MockPlatform(Scheduler scheduler) {
        super(scheduler);
    }

    @Override
    public boolean isMainThread() {
        return false;
    }

    @Override
    public void broadcastMessage(String message) {

    }

    @Override
    public void broadcastJsonMessage(String json) {

    }

    @Override
    public void dispatchServerCommand(String command) {
        this.dispatchedServerCommands.add(command);
    }

    public List<String> getDispatchedServerCommands() {
        return this.dispatchedServerCommands;
    }

    @Override
    public PlayerWrapper<?> getPlayer(UUID uuid) {
        return null;
    }

    @Override
    public PlayerWrapper<?> getPlayer(String name) {
        return null;
    }

    @Override
    public Collection<PlayerWrapper<?>> getOnlinePlayers() {
        return null;
    }

    @Override
    public int getGlobalPlayerCount() {
        return 0;
    }

    @Override
    public ServerType getType() {
        return null;
    }

    @Override
    public void registerOutgoingPluginChannel(DynamicGuiPlugin plugin, String channel) {

    }

    @Override
    public void registerIncomingPluginChannel(DynamicGuiPlugin plugin, String channel, MessagingRunnable runnable) {

    }

    @Override
    public WorldWrapper<?> getWorld(String worldName) {
        return this.worlds.get(worldName);
    }

    public void addWorld(WorldWrapper<?> world) {
        this.worlds.put(world.getName(), world);
    }
}