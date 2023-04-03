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

package com.clubobsidian.dynamicgui.api.manager.cooldown;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class CooldownManager {

    @Inject
    private static CooldownManager instance;

    public static CooldownManager get() {
        return instance;
    }

    /**
     * Gets the remaining cooldown in milliseconds.
     *
     * @param playerWrapper the player wrapper to check the remaining cooldown for
     * @param name          the name of the cooldown
     * @return the cooldown or -1 if no cooldown exists
     */
    public abstract long getRemainingCooldown(@NotNull PlayerWrapper<?> playerWrapper, @NotNull String name);

    /**
     * Gets the remaining cooldown in milliseconds.
     *
     * @param uuid the uuid to check the remaining cooldown for
     * @param name the name of the cooldown
     * @return the cooldown or -1 if no cooldown exists
     */
    public abstract long getRemainingCooldown(@NotNull UUID uuid, @NotNull String name);

    /**
     * Gets the remaining cooldown in milliseconds.
     *
     * @param cooldown the remaining cooldown for a given cooldown
     * @return the cooldown or -1 if no cooldown exists
     */
    public long getRemainingCooldown(@NotNull Cooldown cooldown) {
        long currentTime = System.currentTimeMillis();
        long creationTime = cooldown.getCreationTime();
        long duration = cooldown.getCooldownDuration();

        if ((currentTime - creationTime) >= duration) {
            return -1L;
        } else {
            return duration - (currentTime - creationTime);
        }
    }

    /**
     * Check if the cooldown is on cooldown
     *
     * @param playerWrapper the playerWrapper to check for
     * @param name          the name of the cooldown to check for
     * @return if the given named cooldown is on cooldown
     */
    public boolean isOnCooldown(@NotNull PlayerWrapper<?> playerWrapper, @NotNull String name) {
        return this.isOnCooldown(playerWrapper.getUniqueId(), name);
    }

    /**
     * Check if the cooldown is on cooldown
     *
     * @param uuid the uuid to check for
     * @param name the name of the cooldown to check for
     * @return if the given named cooldown is on cooldown
     */
    public boolean isOnCooldown(@NotNull UUID uuid, @NotNull String name) {
        return this.getRemainingCooldown(uuid, name) != -1L;
    }

    /**
     * Gets the cooldowns for a player
     *
     * @param playerWrapper the playerWrapper to check for
     * @return if the given named cooldown is on cooldown
     */
    @Unmodifiable
    public List<Cooldown> getCooldowns(@NotNull PlayerWrapper<?> playerWrapper) {
        Objects.requireNonNull(playerWrapper);
        UUID uuid = playerWrapper.getUniqueId();
        return this.getCooldowns(uuid);
    }

    /**
     * Gets the cooldowns for a player
     *
     * @param uuid the uuid to check for
     * @return if the given named cooldown is on cooldown
     */
    @Unmodifiable
    public abstract List<Cooldown> getCooldowns(@NotNull UUID uuid);

    /**
     * Creates a cooldown
     *
     * @param playerWrapper    the playerWrapper to create a cooldown for
     * @param name             the name of the cooldown
     * @param cooldownDuration the duration of the cooldown in milliseconds
     * @return the created cooldown
     */
    public Cooldown createCooldown(@NotNull PlayerWrapper<?> playerWrapper,
                                   @NotNull String name, long cooldownDuration) {
        UUID uuid = playerWrapper.getUniqueId();
        return this.createCooldown(uuid, name, cooldownDuration);
    }

    /**
     * Creates a cooldown
     *
     * @param uuid             the uuid to create a cooldown for
     * @param name             the name of the cooldown
     * @param cooldownDuration the duration of the cooldown in milliseconds
     * @return the created cooldown
     */
    public abstract Cooldown createCooldown(@NotNull UUID uuid, @NotNull String name, long cooldownDuration);

    /**
     * Removes a cooldown for a player
     *
     * @param playerWrapper the playerWrapper to remove from
     * @param name          the name of the cooldown
     * @return if the cooldown was removed
     */
    public boolean removeCooldown(@NotNull PlayerWrapper<?> playerWrapper, @NotNull String name) {
        return this.removeCooldown(playerWrapper.getUniqueId(), name);
    }

    /**
     * Removes a cooldown for a player
     *
     * @param uuid the uuid to remove from
     * @param name the name of the cooldown
     * @return if the cooldown was removed
     */
    public abstract boolean removeCooldown(@NotNull UUID uuid, @NotNull String name);

    /**
     * Shuts down and saves the cooldown manager
     */
    public abstract void shutdown();

}