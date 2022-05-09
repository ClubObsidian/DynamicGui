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

package com.clubobsidian.dynamicgui.core.test.mock.world;

import com.clubobsidian.dynamicgui.core.world.WorldWrapper;

public class MockWorldWrapper extends WorldWrapper<MockWorld> {

    private final MockWorld world;

    public MockWorldWrapper(MockWorld world) {
        super(world.getName());
        this.world = world;
    }

    @Override
    public MockWorld getWorld() {
        return this.world;
    }

    @Override
    public void setGameRule(String key, String value) {
        this.world.setGameRule(key, value);
    }

    @Override
    public String getGameRule(String rule) {
        return this.world.getGameRule(rule);
    }
}
