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

package com.clubobsidian.dynamicgui.api.effect;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

@ApiStatus.Internal
public class SoundWrapper implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8096584636206059158L;

    @ApiStatus.Internal
    public static final String TEST_SOUND_STRING = "ambient_cave,1.0,1.0";

    private final SoundData data;

    public SoundWrapper(@NotNull String str) {
        this(SoundData.fromString(Objects.requireNonNull(str)));
    }

    public SoundWrapper(@NotNull SoundData data) {
        this.data = Objects.requireNonNull(data);
    }

    public SoundData getData() {
        return this.data;
    }

    public void playSoundToPlayer(@NotNull PlayerWrapper<?> player) {
        Objects.requireNonNull(player);
        player.playSound(this.data);
    }

    public static class SoundData {

        public static SoundData fromString(@NotNull String str) {
            Objects.requireNonNull(str);
            if (str.contains(",")) {
                String[] args = str.split(",");
                if (args.length == 3) {
                    try {
                        String sound = args[0];
                        float volume = Float.parseFloat(args[1]);
                        float pitch = Float.parseFloat(args[2]);
                        return new SoundData(sound, volume, pitch);
                    } catch (NumberFormatException ex) {
                        //Don't do anything if invalid format
                    }
                }
            }
            return new SoundData(null, 0f, 0f);
        }

        private final String sound;
        private final float volume;
        private final float pitch;

        private SoundData(@NotNull String sound, float volume, float pitch) {
            this.sound = Objects.requireNonNull(sound);
            this.volume = volume;
            this.pitch = pitch;
        }

        public String getSound() {
            return this.sound;
        }

        public float getVolume() {
            return this.volume;
        }

        public float getPitch() {
            return this.pitch;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof SoundData)) {
                return false;
            }
            SoundData soundData = (SoundData) o;
            return Float.compare(soundData.volume, this.volume) == 0
                    && Float.compare(soundData.pitch, this.pitch) == 0
                    && Objects.equals(this.sound, soundData.sound);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.sound, this.volume, this.pitch);
        }
    }
}