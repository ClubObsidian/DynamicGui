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

package com.clubobsidian.dynamicgui.core.test.function;

import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.core.DynamicGuiImpl;
import com.clubobsidian.dynamicgui.core.function.SendFunction;
import com.clubobsidian.dynamicgui.api.proxy.Proxy;
import com.clubobsidian.dynamicgui.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SendFunctionTest extends FactoryTest {

    @Test
    public void nullTest() throws Exception {
        Function function = new SendFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void noProxyTest() throws Exception {
        this.getFactory().inject();
        Function function = new SendFunction();
        function.setData("test");
        assertTrue(this.setProxy(Proxy.NONE));
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void bungeeCordTest() throws Exception {
        this.getFactory().inject();
        Function function = new SendFunction();
        function.setData("test");
        assertTrue(this.setProxy(Proxy.BUNGEE));
        assertTrue(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void redisBungeeTest() throws Exception {
        this.getFactory().inject();
        Function function = new SendFunction();
        function.setData("test");
        assertTrue(this.setProxy(Proxy.REDIS_BUNGEE));
        assertTrue(function.function(this.getFactory().createPlayer()));
    }

    private boolean setProxy(Proxy proxy) {
        try {
            Field proxyField = DynamicGuiImpl.class.getDeclaredField("proxy");
            proxyField.setAccessible(true);
            proxyField.set(DynamicGui.get(), proxy);
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
}
