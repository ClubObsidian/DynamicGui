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

package com.clubobsidian.dynamicgui.bukkit.listener;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.api.world.LocationWrapper;
import com.clubobsidian.dynamicgui.bukkit.entity.BukkitPlayerWrapper;
import com.clubobsidian.dynamicgui.core.event.player.PlayerAction;
import com.clubobsidian.trident.EventBus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import jakarta.inject.Inject;

public class PlayerInteractListener implements Listener {

    @Inject
    private EventBus eventBus;

    @EventHandler
    public void interact(final PlayerInteractEvent e) {
        if (e.getClickedBlock() != null) {
            PlayerAction action = PlayerAction.valueOf(e.getAction().toString());
            PlayerWrapper<?> playerWrapper = new BukkitPlayerWrapper<>(e.getPlayer());
            LocationWrapper<?> locationWrapper = LocationManager.get().toLocationWrapper(e.getClickedBlock().getLocation());
            com.clubobsidian.dynamicgui.core.event.block.PlayerInteractEvent interactEvent = new com.clubobsidian.dynamicgui.core.event.block.PlayerInteractEvent(playerWrapper, locationWrapper, action);
            this.eventBus.callEvent(interactEvent);
            if (interactEvent.isCancelled()) {
                e.setCancelled(true);
            }
        }
    }
}