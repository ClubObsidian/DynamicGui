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

package com.clubobsidian.dynamicgui.api.platform;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.messaging.MessagingRunnable;
import com.clubobsidian.dynamicgui.api.world.WorldWrapper;
import com.clubobsidian.dynamicgui.api.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.api.scheduler.Scheduler;

import java.util.Collection;
import java.util.UUID;

public interface Platform {

    Scheduler getScheduler();

    boolean isMainThread();

    void broadcastMessage(String message);

    void broadcastJsonMessage(String json);

    void dispatchServerCommand(String command);

    PlayerWrapper<?> getPlayer(UUID uuid);

    PlayerWrapper<?> getPlayer(String name);

    Collection<PlayerWrapper<?>> getOnlinePlayers();

    int getLocalPlayerCount();

    PlatformType getType();

    void registerOutgoingPluginChannel(DynamicGuiPlugin plugin, String channel);

    void registerIncomingPluginChannel(DynamicGuiPlugin plugin, String channel, MessagingRunnable runnable);

    WorldWrapper<?> getWorld(String worldName);

    default boolean syncCommands() {
        return false;
    }
}