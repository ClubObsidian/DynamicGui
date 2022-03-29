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

package com.clubobsidian.dynamicgui.core.proxy;

import java.util.Locale;

public enum Proxy {

    BUNGEECORD,
    REDIS_BUNGEE,
    NONE;

    public static Proxy fromString(String proxyStr) {
        if (proxyStr.toLowerCase(Locale.ROOT).startsWith("bungee")) {
            return Proxy.BUNGEECORD;
        } else if (proxyStr.toLowerCase(Locale.ROOT).startsWith("redis")) {
            return Proxy.REDIS_BUNGEE;
        }
        return Proxy.NONE;
    }
}
