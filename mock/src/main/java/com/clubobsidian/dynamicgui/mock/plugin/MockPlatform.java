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

package com.clubobsidian.dynamicgui.mock.plugin;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.world.WorldWrapper;
import com.clubobsidian.dynamicgui.api.messaging.MessagingRunnable;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.api.platform.PlatformType;
import com.clubobsidian.dynamicgui.api.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.api.scheduler.Scheduler;
import com.clubobsidian.dynamicgui.mock.scheduler.MockScheduler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class MockPlatform implements Platform {

    private final Map<String, WorldWrapper<?>> worlds = new HashMap<>();
    private final List<String> dispatchedServerCommands = new ArrayList<>();
    private final MockScheduler scheduler = new MockScheduler();
    private final List<String> broadcastMessages = new ArrayList<>();
    private final List<PlayerWrapper<?>> players = new ArrayList<>();

    private final List<ChannelData> outgoingData = new ArrayList<>();
    private final List<ChannelData> incomingData = new ArrayList<>();

    @Override
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    @Override
    public boolean isMainThread() {
        return this.scheduler.isOnMainThread();
    }

    @Override
    public void broadcastMessage(String message) {
        this.broadcastMessages.add(message);
    }

    public List<String> getBroadcastMessages() {
        return this.broadcastMessages;
    }

    @Override
    public void broadcastJsonMessage(String json) {
        this.broadcastMessages.add(json);
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
        for (PlayerWrapper<?> player : this.players) {
            if (player.getUniqueId().equals(uuid)) {
                return player;
            }
        }
        return null;
    }

    @Override
    public PlayerWrapper<?> getPlayer(String name) {
        for (PlayerWrapper<?> player : this.players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    @Override
    public Collection<PlayerWrapper<?>> getOnlinePlayers() {
        return this.players;
    }

    @Override
    public int getLocalPlayerCount() {
        return 0;
    }

    @Override
    public PlatformType getType() {
        return null;
    }

    @Override
    public void registerOutgoingPluginChannel(DynamicGuiPlugin plugin, String channel) {
        this.outgoingData.add(new ChannelData(plugin, channel, null));
    }

    @Override
    public void registerIncomingPluginChannel(DynamicGuiPlugin plugin, String channel, MessagingRunnable runnable) {
        this.incomingData.add(new ChannelData(plugin, channel, runnable));
    }

    public List<ChannelData> getOutgoingData() {
        return this.outgoingData;
    }

    public List<ChannelData> getIncomingData() {
        return this.incomingData;
    }

    @Override
    public WorldWrapper<?> getWorld(String worldName) {
        return this.worlds.get(worldName);
    }

    public void addWorld(WorldWrapper<?> world) {
        this.worlds.put(world.getName(), world);
    }


    public class ChannelData {

        private final DynamicGuiPlugin plugin;
        private final String channel;
        private final MessagingRunnable runnable;

        public ChannelData(@NotNull DynamicGuiPlugin plugin,
                           @NotNull String channel,
                           @Nullable MessagingRunnable runnable) {
            this.plugin = Objects.requireNonNull(plugin);
            this.channel = Objects.requireNonNull(channel);
            this.runnable = runnable;
        }

        public DynamicGuiPlugin getPlugin() {
            return this.plugin;
        }

        public String getChannel() {
            return this.channel;
        }

        public @Nullable MessagingRunnable getRunnable() {
            return this.runnable;
        }
    }
}
