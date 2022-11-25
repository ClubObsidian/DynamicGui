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

package com.clubobsidian.dynamicgui.core.manager.cooldown;

import com.clubobsidian.dynamicgui.api.manager.cooldown.Cooldown;

public class SimpleCooldown implements Cooldown {


    private final String name;
    private final long time;
    private final long cooldown;

    public SimpleCooldown(String name, long time, long cooldown) {
        this.name = name;
        this.time = time;
        this.cooldown = cooldown;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public long getTime() {
        return this.time;
    }

    @Override
    public long getCooldown() {
        return this.cooldown;
    }
}