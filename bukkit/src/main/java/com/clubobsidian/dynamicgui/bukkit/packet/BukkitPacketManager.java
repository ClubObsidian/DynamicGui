/*
 *    Copyright 2018-2025 virustotalop
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

package com.clubobsidian.dynamicgui.bukkit.packet;

import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.core.packet.PacketEventsManager;
import com.clubobsidian.trident.EventBus;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import jakarta.inject.Inject;
import org.jetbrains.annotations.NotNull;

public class BukkitPacketManager extends PacketEventsManager {

    @Inject
    public BukkitPacketManager(Platform platform, EventBus eventBus) {
        super(platform, eventBus);
    }

    @Override
    protected ItemStack toPacketItem(@NotNull ItemStackWrapper<?> itemStackWrapper) {
        return SpigotConversionUtil.fromBukkitItemStack((org.bukkit.inventory.ItemStack) itemStackWrapper.getItemStack());
    }
}
