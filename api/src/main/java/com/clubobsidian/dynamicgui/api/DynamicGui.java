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

package com.clubobsidian.dynamicgui.api;

import com.clubobsidian.dynamicgui.api.config.Config;
import com.clubobsidian.dynamicgui.api.economy.Economy;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.api.permission.Permission;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.api.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.api.proxy.Proxy;
import com.clubobsidian.dynamicgui.api.registry.npc.NPCRegistry;
import org.jetbrains.annotations.Unmodifiable;

import javax.inject.Inject;
import java.util.List;

public abstract class DynamicGui {

    @Inject
    private static DynamicGui instance;

    public static DynamicGui get() {
        return instance;
    }

    /**
     * Bootstraps the DynamicGui instance
     *
     * @return whether the bootstrap is successful
     */
    public abstract boolean start();

    /**
     * Stops the DynamicGui instance
     */
    public abstract void stop();

    /**
     * Gets the config instance
     *
     * @return the config
     */
    public abstract Config getConfig();

    /**
     * The configured proxy
     *
     * @return the proxy
     */
    public abstract Proxy getProxy();

    /**
     * The native or platform DynamicGui plugin
     *
     * @return the DynamicGui plugin
     */
    public abstract DynamicGuiPlugin getPlugin();

    /**
     * The current economy instance or
     * a no-op economy instance.
     *
     * @return the economy instance used
     */
    public abstract Economy getEconomy();

    /**
     * The currency permission instance or
     * a no-op permission instance.
     *
     * @return the permission instance used
     */
    public abstract Permission getPermission();

    /**
     * The currently loading npc registries. This
     * may not always reflect all the current npc registries
     * right on start up due to hacks needing to be used to
     * load in problematic plugins like citizens
     *
     * @return the loaded npc registries
     */
    public abstract @Unmodifiable List<NPCRegistry> getNpcRegistries();

    /**
     * Registers a npc registry
     *
     * @param npcRegistry the npc registry to register
     */
    public abstract void registerNPCRegistry(NPCRegistry npcRegistry);

    /**
     * The current running platform
     *
     * @return the platform
     */
    public abstract Platform getPlatform();

    /**
     * The logger wrapper
     *
     * @return logger wrapper
     */
    public abstract LoggerWrapper<?> getLogger();

    /**
     * Returns the global player count
     *
     * @return the global player count
     */
    public abstract int getGlobalPlayerCount();

    /**
     * Gets the player count of a specified server
     *
     * @param server the server to get the player count for
     * @return the server's player count
     */
    public abstract int getServerPlayerCount(String server);

    /**
     * Sends a player to a server
     *
     * @param playerWrapper the player to send
     * @param server        the server to send them to
     * @return if the proxy is configured
     */
    public abstract boolean sendToServer(PlayerWrapper<?> playerWrapper, String server);

    /**
     * Injects fields in a given object
     *
     * @param obj the object to inject
     */
    public abstract void inject(Object obj);
}