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

package com.clubobsidian.dynamicgui.api.manager.cooldown;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public abstract class CooldownManager {

    @Inject
    private static CooldownManager instance;

    public static CooldownManager get() {
        return instance;
    }

    public abstract long getRemainingCooldown(PlayerWrapper<?> playerWrapper, String name);

    public abstract long getRemainingCooldown(UUID uuid, String name);

    public long getRemainingCooldown(Cooldown cooldown) {
        long currentTime = System.currentTimeMillis();
        long creationTime = cooldown.getCreationTime();
        long duration = cooldown.getCooldownDuration();

        if ((currentTime - creationTime) >= duration) {
            return -1L;
        } else {
            return duration - (currentTime - creationTime);
        }
    }

    public boolean isOnCooldown(PlayerWrapper<?> playerWrapper, String name) {
        return this.isOnCooldown(playerWrapper.getUniqueId(), name);
    }

    public boolean isOnCooldown(UUID uuid, String name) {
        return this.getRemainingCooldown(uuid, name) != -1L;
    }

    public List<Cooldown> getCooldowns(PlayerWrapper<?> playerWrapper) {
        UUID uuid = playerWrapper.getUniqueId();
        return this.getCooldowns(uuid);
    }

    public abstract List<Cooldown> getCooldowns(UUID uuid);

    public Cooldown createCooldown(PlayerWrapper<?> playerWrapper, String name, long cooldownTime) {
        UUID uuid = playerWrapper.getUniqueId();
        return this.createCooldown(uuid, name, cooldownTime);
    }

    public abstract Cooldown createCooldown(UUID uuid, String name, long cooldownTime);

    public boolean removeCooldown(PlayerWrapper<?> playerWrapper, String name) {
        return this.removeCooldown(playerWrapper.getUniqueId(), name);
    }

    public abstract boolean removeCooldown(UUID uuid, String name);

    public abstract void shutdown();

}