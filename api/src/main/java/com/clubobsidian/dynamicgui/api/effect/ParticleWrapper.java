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

package com.clubobsidian.dynamicgui.api.effect;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

@ApiStatus.Internal
public class ParticleWrapper implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5003322741003989392L;

    private final ParticleData data;

    public ParticleWrapper(@NotNull String str) {
        this(ParticleData.fromString(Objects.requireNonNull(str)));
    }

    public ParticleWrapper(@NotNull ParticleData data) {
        this.data = Objects.requireNonNull(data);
    }

    /**
     * Gets the ParticleData for the particle effect
     *
     * @return the ParticleData object
     */
    public ParticleData getData() {
        return this.data;
    }

    /**
     * Spawns the particle effect for the player
     *
     * @param player the player to spawn the effect for
     */
    public void spawnEffect(@NotNull PlayerWrapper<?> player) {
        Objects.requireNonNull(player);
        player.playEffect(this.data);
    }

    public static class ParticleData {

        /**
         * Creates a ParticleData object from a string
         * @param str the string to create the ParticleData object from
         * @return ParticleData object from the given string
         */
        public static ParticleData fromString(@NotNull String str) {
            Objects.requireNonNull(str);
            if (str.contains(",")) {
                String[] args = str.split(",");
                return new ParticleData(args[0].toUpperCase(), NumberUtils.toInt(args[1]));
            }
            return new ParticleData(str, 0);
        }

        private final String effect;
        private final int extraData;

        private ParticleData(@NotNull String effect, int data) {
            this.effect = Objects.requireNonNull(effect);
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