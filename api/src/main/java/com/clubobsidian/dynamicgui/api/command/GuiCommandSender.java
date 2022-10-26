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

package com.clubobsidian.dynamicgui.api.command;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.manager.entity.EntityManager;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface GuiCommandSender {

    /**
     * Returns the native or platform specific sender that the command sender wraps
     *
     * @param <T> the native sender type
     * @return the native sender
     */
    @NotNull <T> T getNativeSender();

    /**
     * Returns whether a sender is a player
     *
     * @return If the sender is a player
     */
    boolean isPlayer();

    /**
     * Messages a sender
     *
     * @param message the message to send to the sender
     */
    void sendMessage(@NotNull String message);

    /**
     * An optional if a player exists
     *
     * @return an optional if the player exists
     */
    default Optional<PlayerWrapper<?>> getPlayer() {
        return this.isPlayer() ?
                Optional.of(EntityManager
                        .get()
                        .createPlayerWrapper(this.getNativeSender())) : Optional.empty();
    }
}