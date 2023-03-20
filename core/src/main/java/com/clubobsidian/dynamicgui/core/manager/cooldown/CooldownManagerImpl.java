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

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.manager.cooldown.Cooldown;
import com.clubobsidian.dynamicgui.api.manager.cooldown.CooldownManager;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.api.plugin.DynamicGuiPlugin;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class CooldownManagerImpl extends CooldownManager {

    private final Map<UUID, Map<String, Cooldown>> cooldowns = new ConcurrentHashMap<>();
    private final AtomicBoolean updateConfig = new AtomicBoolean(false);
    private final Configuration cooldownConfig;

    @Inject
    private CooldownManagerImpl(DynamicGuiPlugin plugin, Platform platform) {
        this.cooldownConfig = this.loadConfig(plugin);
        this.scheduleCooldownUpdate(platform);
        this.scheduleConfigUpdate(platform);
    }

    private Configuration loadConfig(DynamicGuiPlugin plugin) {
        File dataFolder = plugin.getDataFolder();
        File cooldownsFile = new File(dataFolder, "cooldowns.yml");
        Configuration config = Configuration.load(cooldownsFile);
        for (String uuidStr : config.getKeys()) {
            ConfigurationSection section = config.getConfigurationSection(uuidStr);
            Map<String, Cooldown> cooldownMap = new ConcurrentHashMap<>();
            for (String cooldownName : section.getKeys()) {
                long time = section.getLong(cooldownName + ".time");
                long cooldown = section.getLong(cooldownName + ".cooldown");
                Cooldown cooldownObj = new SimpleCooldown(cooldownName, time, cooldown);
                if (this.getRemainingCooldown(cooldownObj) != -1L) {
                    cooldownMap.put(cooldownName, cooldownObj);
                } else {
                    section.set(cooldownName, null);
                }
            }
            if (section.isEmpty()) {
                config.set(uuidStr, null);
            }
            if (cooldownMap.size() > 0) {
                UUID uuid = UUID.fromString(uuidStr);
                this.cooldowns.put(uuid, cooldownMap);
            }
        }
        config.save();
        return config;
    }

    @Override
    public long getRemainingCooldown(@NotNull PlayerWrapper<?> playerWrapper, @NotNull String name) {
        Objects.requireNonNull(playerWrapper);
        Objects.requireNonNull(name);
        return this.getRemainingCooldown(playerWrapper.getUniqueId(), name);
    }

    @Override
    public long getRemainingCooldown(@NotNull UUID uuid, @NotNull String name) {
        Objects.requireNonNull(uuid);
        Objects.requireNonNull(name);
        Map<String, Cooldown> cooldownMap = this.cooldowns.get(uuid);
        if (cooldownMap == null) {
            return -1L;
        }

        Cooldown cooldown = cooldownMap.get(name);
        if (cooldown == null) {
            return -1L;
        }

        return this.getRemainingCooldown(cooldown);
    }

    @Override
    public List<Cooldown> getCooldowns(@NotNull UUID uuid) {
        Objects.requireNonNull(uuid);
        Map<String, Cooldown> cooldowns = this.cooldowns.get(uuid);
        if (cooldowns == null) {
            return Collections.emptyList();
        }
        return List.copyOf(cooldowns.values());
    }

    @Override
    public Cooldown createCooldown(@NotNull UUID uuid, @NotNull String name, long cooldownDuration) {
        Objects.requireNonNull(uuid);
        Objects.requireNonNull(name);
        long cooldownRemaining = this.getRemainingCooldown(uuid, name);
        if (cooldownRemaining == -1L) {
            long currentTime = System.currentTimeMillis();
            Cooldown cooldown = new SimpleCooldown(name, currentTime, cooldownDuration);
            Map<String, Cooldown> cooldownMap = this.cooldowns.get(uuid);
            if (cooldownMap == null) {
                cooldownMap = new ConcurrentHashMap<>();
                this.cooldowns.put(uuid, cooldownMap);
            }
            this.updateConfig.set(true);
            cooldownMap.put(name, cooldown);
            return cooldown;
        }
        return null;
    }

    @Override
    public boolean removeCooldown(@NotNull UUID uuid, @NotNull String name) {
        Objects.requireNonNull(uuid);
        Objects.requireNonNull(name);
        Map<String, Cooldown> cooldownMap = this.cooldowns.get(uuid);
        if (cooldownMap == null) {
            return false;
        }
        boolean removed = cooldownMap.remove(name) != null;
        if (removed) {
            this.updateConfig.set(true);
        }
        return removed;
    }

    @Override
    public void shutdown() {
        this.updateAndSaveConfig();
    }

    private void updateAndSaveConfig() {
        for (Map.Entry<UUID, Map<String, Cooldown>> next : this.cooldowns.entrySet()) {
            UUID uuid = next.getKey();
            String uuidStr = uuid.toString();
            Map<String, Cooldown> cooldownMap = next.getValue();
            for (Map.Entry<String, Cooldown> entry : cooldownMap.entrySet()) {
                String cooldownName = entry.getKey();
                Cooldown cooldownObj = entry.getValue();
                long time = cooldownObj.getCreationTime();
                long cooldown = cooldownObj.getCooldownDuration();
                ConfigurationSection cooldownSection = this.cooldownConfig.getConfigurationSection(uuidStr + "." + cooldownName);
                cooldownSection.set("time", time);
                cooldownSection.set("cooldown", cooldown);
            }
        }
        this.cooldownConfig.save();
    }

    private void updateCache() {
        Iterator<Map.Entry<UUID, Map<String, Cooldown>>> it = this.cooldowns.entrySet().iterator();
        boolean modified = false;
        while (it.hasNext()) {
            Map.Entry<UUID, Map<String, Cooldown>> next = it.next();
            UUID uuid = next.getKey();
            String uuidStr = uuid.toString();
            Map<String, Cooldown> cooldownMap = next.getValue();
            Iterator<Map.Entry<String, Cooldown>> cooldownIt = cooldownMap.entrySet().iterator();
            while (cooldownIt.hasNext()) {
                Map.Entry<String, Cooldown> cooldownNext = cooldownIt.next();
                String cooldownName = cooldownNext.getKey();
                Cooldown cooldown = cooldownNext.getValue();
                long cooldownRemaining = this.getRemainingCooldown(cooldown);
                if (cooldownRemaining == -1L) {
                    cooldownIt.remove();
                    this.cooldownConfig.set(uuidStr + "." + cooldownName, null);
                    modified = true;
                }
            }
        }
        if (modified) {
            this.updateConfig.set(true);
        }
    }

    private void scheduleCooldownUpdate(Platform platform) {
        platform.getScheduler().scheduleSyncRepeatingTask(this::updateCache, 1L, 1L);
    }

    private void scheduleConfigUpdate(Platform platform) {
        platform.getScheduler().scheduleAsyncRepeatingTask(() -> {
            if (this.updateConfig.get()) {
                this.updateConfig.set(false);
                this.updateAndSaveConfig();
            }
        }, 1L, 1L);
    }
}