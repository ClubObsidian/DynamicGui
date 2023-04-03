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

package com.clubobsidian.dynamicgui.core.test.proxy;

import com.clubobsidian.dynamicgui.api.messaging.MessagingRunnable;
import com.clubobsidian.dynamicgui.api.proxy.Proxy;
import com.clubobsidian.dynamicgui.core.test.mock.plugin.MockPlatform;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProxyTest extends FactoryTest {

    private static final MessagingRunnable RUNNABLE = (wrapper, data) -> {};

    @Test
    public void noNullAliasesTest() {
        for (Proxy proxy : Proxy.values()) {
            assertNotNull(proxy.getAliases(), proxy.name() + " has a null alias!");
        }

    }

    @Test
    public void noProxyFromStringTest() {
        assertEquals(Proxy.NONE, Proxy.fromString(UUID.randomUUID().toString()));
    }

    @Test
    public void bungeeFromStringTest() {
        assertEquals(Proxy.BUNGEE, Proxy.fromString("BUNGEE"));
    }


    @Test
    public void velocityFromStringTest() {
        assertEquals(Proxy.VELOCITY, Proxy.fromString("VELOCITY"));
    }


    @Test
    public void redisBungeeFromStringTest() {
        assertEquals(Proxy.REDIS_BUNGEE, Proxy.fromString("REDIS"));
    }

    @Test
    public void noProxyProtocolRegisterTest() {
        MockPlatform platform = this.getFactory().getPlatform();
        Proxy.NONE.getProtocol().register(platform, this.getFactory().getPlugin(), RUNNABLE);
        assertTrue(platform.getIncomingData().size() == 0);
        assertTrue(platform.getOutgoingData().size() == 0);
    }

    @Test
    public void bungeeProtocolRegisterTest() {
        MockPlatform platform = this.getFactory().getPlatform();
        Proxy.BUNGEE.getProtocol().register(platform, this.getFactory().getPlugin(), RUNNABLE);
        assertTrue(platform.getIncomingData().size() == 1);
        assertTrue(platform.getOutgoingData().size() == 1);
    }

    @Test
    public void velocityProtocolRegisterTest() {
        MockPlatform platform = this.getFactory().getPlatform();
        Proxy.VELOCITY.getProtocol().register(platform, this.getFactory().getPlugin(), RUNNABLE);
        assertTrue(platform.getIncomingData().size() == 1);
        assertTrue(platform.getOutgoingData().size() == 1);
    }

    @Test
    public void redisBungeeProtocolRegisterTest() {
        MockPlatform platform = this.getFactory().getPlatform();
        Proxy.REDIS_BUNGEE.getProtocol().register(platform, this.getFactory().getPlugin(), RUNNABLE);
        assertTrue(platform.getIncomingData().size() == 1);
        assertTrue(platform.getOutgoingData().size() == 2);
    }
}