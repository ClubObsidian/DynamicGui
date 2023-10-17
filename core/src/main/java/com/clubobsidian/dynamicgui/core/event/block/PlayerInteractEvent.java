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

package com.clubobsidian.dynamicgui.core.event.block;


import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.world.LocationWrapper;
import com.clubobsidian.dynamicgui.core.event.LocationEvent;
import com.clubobsidian.dynamicgui.core.event.player.PlayerAction;
import com.clubobsidian.trident.Cancellable;

public class PlayerInteractEvent extends LocationEvent implements Cancellable {

    private final PlayerAction action;
    private boolean canceled;

    public PlayerInteractEvent(PlayerWrapper<?> playerWrapper, LocationWrapper<?> locationWrapper, PlayerAction action) {
        super(playerWrapper, locationWrapper);
        this.action = action;
        this.canceled = false;
    }

    /**
     * Gets the action the player performed
     * @return the PlayerAction the player performed
     */
    public PlayerAction getAction() {
        return this.action;
    }

    /**
     * @return true if the event is cancelled, false otherwise
     */
    @Override
    public boolean isCancelled() {
        return this.canceled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.canceled = cancel;
    }
}