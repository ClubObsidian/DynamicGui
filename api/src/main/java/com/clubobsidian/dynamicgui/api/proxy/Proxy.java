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

package com.clubobsidian.dynamicgui.api.proxy;

import java.util.Locale;

public enum Proxy {

    BUNGEE("bungee", "velocity"),
    REDIS_BUNGEE("redis"),
    NONE;

    String[] aliases;

    Proxy(String... aliases) {
        this.aliases = aliases;
    }

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
}