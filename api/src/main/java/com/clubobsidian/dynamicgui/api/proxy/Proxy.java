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

package com.clubobsidian.dynamicgui.api.proxy;

import com.clubobsidian.dynamicgui.api.messaging.MessagingRunnable;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.api.plugin.DynamicGuiPlugin;

import java.util.Locale;

public enum Proxy {

    BUNGEE(Protocol.BUNGEE, "bungeecord", "bungee"),
    VELOCITY(Protocol.BUNGEE, "velocity"),
    REDIS_BUNGEE(Protocol.REDIS_BUNGEE, "redisbungee", "redis"),
    NONE(Protocol.NONE, "none");

    private final Protocol protocol;
    private final String[] aliases;

    Proxy(Protocol protocol, String... aliases) {
        this.protocol = protocol;
        this.aliases = aliases;
    }

    /**
     * Returns a proxy from its string alias
     * @param proxyStr
     * @return
     */
    public static Proxy fromString(final String proxyStr) {
        String localeStr = proxyStr.toLowerCase(Locale.ROOT);
        for (Proxy proxy : Proxy.values()) {
            for (String alias : proxy.aliases) {
                if (localeStr.startsWith(alias)) {
                    return proxy;
                }
            }
        }
        return Proxy.NONE;
    }

    /**
     * Gets the aliases of a proxy
     * @return The aliases of the proxy
     */
    public String[] getAliases() {
        return this.aliases;
    }


    /**
     * Gets the protocol of the proxy
     * @return The protocol of the proxy
     */
    public Protocol getProtocol() {
        return this.protocol;
    }

    public enum Protocol {
        BUNGEE((platform, plugin, runnable) -> {
            platform.registerOutgoingPluginChannel(plugin, "BungeeCord");
            platform.registerIncomingPluginChannel(plugin, "BungeeCord", runnable);
        }),
        REDIS_BUNGEE((platform, plugin, runnable) -> {
            platform.registerOutgoingPluginChannel(plugin, "RedisBungee");
            platform.registerOutgoingPluginChannel(plugin, "BungeeCord");
            platform.registerIncomingPluginChannel(plugin, "RedisBungee", runnable);
        }),
        NONE((platform, plugin, runnable) -> {
        });

        private final Registrable registrable;

        Protocol(Registrable registrable) {
            this.registrable = registrable;
        }

        public void register(Platform platform, DynamicGuiPlugin plugin, MessagingRunnable runnable) {
            this.registrable.register(platform, plugin, runnable);
        }

        @FunctionalInterface
        private interface Registrable {

            void register(Platform platform, DynamicGuiPlugin plugin, MessagingRunnable runnable);

        }
    }
}