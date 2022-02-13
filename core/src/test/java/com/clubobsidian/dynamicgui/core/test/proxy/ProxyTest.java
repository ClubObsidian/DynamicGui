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

package com.clubobsidian.dynamicgui.core.test.proxy;

import com.clubobsidian.dynamicgui.core.proxy.Proxy;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProxyTest {

    @Test
    public void noProxyTest() {
        assertEquals(Proxy.NONE, Proxy.fromString(UUID.randomUUID().toString()));
    }

    @Test
    public void bungeeTest() {;
        assertEquals(Proxy.BUNGEECORD, Proxy.fromString("BUNGEE"));
    }

    @Test
    public void redisBungeeTest() {
        assertEquals(Proxy.REDIS_BUNGEE, Proxy.fromString("REDIS"));
    }
}