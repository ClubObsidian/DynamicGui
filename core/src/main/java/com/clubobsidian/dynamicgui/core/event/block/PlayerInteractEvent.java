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
package com.clubobsidian.dynamicgui.core.event.block;


import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.event.LocationEvent;
import com.clubobsidian.dynamicgui.core.event.player.PlayerAction;
import com.clubobsidian.dynamicgui.core.world.LocationWrapper;
import com.clubobsidian.trident.Cancellable;

public class PlayerInteractEvent extends LocationEvent implements Cancellable {

    private final PlayerAction action;
    private boolean canceled;

    public PlayerInteractEvent(PlayerWrapper<?> playerWrapper, LocationWrapper<?> locationWrapper, PlayerAction action) {
        super(playerWrapper, locationWrapper);
        this.action = action;
        this.canceled = false;
    }

    public PlayerAction getAction() {
        return this.action;
    }

    @Deprecated
    public boolean isCanceled() {
        return this.canceled;
    }

    @Deprecated
    public void setCanceled(boolean cancel) {
        this.canceled = cancel;
    }

    @Override
    public boolean isCancelled() {
        return this.canceled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.canceled = cancel;
    }
}