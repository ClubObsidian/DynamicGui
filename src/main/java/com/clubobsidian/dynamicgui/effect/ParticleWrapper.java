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
package com.clubobsidian.dynamicgui.effect;

import java.io.Serializable;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;

public class ParticleWrapper implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5003322741003989392L;
    private String effect;
    private int data;

    public ParticleWrapper(String str) {
        this.loadFromString(str);
    }

    private void loadFromString(String str) {
        if (str.contains(",")) {
            String[] args = str.split(",");
            this.effect = args[0].toUpperCase();
            this.data = Integer.parseInt(args[1]);
        } else {
            this.effect = str;
            this.data = 0;
        }
    }

    public void spawnEffect(PlayerWrapper<?> player) {
        player.playEffect(this.effect.toUpperCase(), this.data);
    }
}