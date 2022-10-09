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

package com.clubobsidian.dynamicgui.api;

import com.clubobsidian.dynamicgui.api.config.Message;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.api.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.api.proxy.Proxy;
import com.clubobsidian.trident.EventBus;

import javax.inject.Inject;

public abstract class DynamicGui {

    @Inject
    private static DynamicGui instance;

    public static DynamicGui get() {
        return instance;
    }


    public abstract boolean start();

    public abstract void stop();

    public abstract Message getMessage();

    public abstract Proxy getProxy();

    public abstract String getDateTimeFormat();

    public abstract DynamicGuiPlugin getPlugin();

    public abstract Platform getPlatform();

    public abstract LoggerWrapper<?> getLogger();

    public abstract int getGlobalServerPlayerCount();

    public abstract int getServerPlayerCount(String server);

    public abstract boolean sendToServer(PlayerWrapper<?> playerWrapper, String server);

    public abstract EventBus getEventBus();

}