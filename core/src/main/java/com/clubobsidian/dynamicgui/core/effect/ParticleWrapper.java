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
package com.clubobsidian.dynamicgui.core.effect;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.util.Objects;

public class ParticleWrapper implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5003322741003989392L;

    private final ParticleData data;

    public ParticleWrapper(String str) {
        this(ParticleData.fromString(str));
    }

    public ParticleWrapper(ParticleData data) {
        this.data = data;
    }

    public ParticleData getData() {
        return this.data;
    }

    public void spawnEffect(PlayerWrapper<?> player) {
        player.playEffect(this.data);
    }

    public static class ParticleData {

        public static ParticleData fromString(String str) {
            if (str.contains(",")) {
                String[] args = str.split(",");
                return new ParticleData(args[0].toUpperCase(), NumberUtils.toInt(args[1]));
            }
            return new ParticleData(str, 0);
        }

        private final String effect;
        private final int extraData;

        private ParticleData(String effect, int data) {
            this.effect = effect;
            this.extraData = data;
        }

        public String getEffect() {
            return this.effect;
        }

        public int getExtraData() {
            return this.extraData;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ParticleData)) return false;
            ParticleData that = (ParticleData) o;
            return extraData == that.extraData && Objects.equals(effect, that.effect);
        }

        @Override
        public int hashCode() {
            return Objects.hash(effect, extraData);
        }
    }
}