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

package com.clubobsidian.dynamicgui.api.entity;

import com.clubobsidian.dynamicgui.api.effect.ParticleWrapper;
import com.clubobsidian.dynamicgui.api.effect.SoundWrapper;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.world.LocationWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class PlayerWrapper<T> extends EntityWrapper<T> {

    public PlayerWrapper(@NotNull T player) {
        super(player);
    }

    /**
     * Gets the player's name
     *
     * @return the player's name
     */
    public abstract String getName();

    /**
     * Gets the unique id(uuid) for the player
     *
     * @return the unique id(uuid) of the player
     */
    public abstract UUID getUniqueId();

    /**
     * Make a player send a chat message
     *
     * @param message the message for the player to send
     */
    public abstract void chat(@NotNull String message);

    /**
     * Sends a player a message
     *
     * @param message the message to send to the player
     */
    public abstract void sendMessage(@NotNull String message);

    /**
     * Sends a player a json message
     *
     * @param json to send to the player
     */
    public abstract void sendJsonMessage(@NotNull String json);

    /**
     * Check if a player has a permission
     *
     * @param permission the permission to check for
     * @return whether the player has the permission
     */
    public abstract boolean hasPermission(@NotNull String permission);

    /**
     * Adds a permission to a player
     *
     * @param permission to add to the player
     * @return whether the permission was added
     */
    public abstract boolean addPermission(@NotNull String permission);

    /**
     * Removes a permission from a player
     *
     * @param permission to remove from the player
     * @return whether the permission was removed
     */
    public abstract boolean removePermission(@NotNull String permission);

    public abstract int getExperience();

    public abstract void setExperience(int experience);

    public abstract int getLevel();

    /**
     * Gets the open inventory for a player as an InventoryWrapper
     *
     * @return the current opened inventory as a wrapper
     */
    @Nullable
    public abstract InventoryWrapper<?> getOpenInventoryWrapper();

    /**
     * The item in the player's hand as a ItemStackWrapper
     *
     * @return the player's current item in hand as a wrapper
     */
    public abstract ItemStackWrapper<?> getItemInHand();

    /**
     * Close the player's current open inventory
     */
    public abstract void closeInventory();

    /**
     * Opens an inventory
     *
     * @param inventoryWrapper the inventory wrapper to open
     */
    public abstract void openInventory(@NotNull InventoryWrapper<?> inventoryWrapper);

    /**
     * Sends a plugin message to the player, supports BungeeCord messaging style
     *
     * @param channel to send the message on
     * @param message the message to send
     */
    public abstract void sendPluginMessage(@NotNull String channel, byte[] message);

    /**
     * Plays a sound for a player
     *
     * @param soundData to player to the player
     */
    public abstract void playSound(@NotNull SoundWrapper.SoundData soundData);

    /**
     * Plays a particle to a player
     *
     * @param particleData to play to the player
     */
    public abstract void playEffect(@NotNull ParticleWrapper.ParticleData particleData);

    /**
     * Update's the player's current open inventory, this is a hack but is still needed
     * on certain platforms.
     */
    public abstract void updateInventory();

    /**
     * Gets the player's current location
     *
     * @return the player's current location as a LocationWrapper
     */
    public abstract LocationWrapper<?> getLocation();

    /**
     * Check to see if a player is online
     *
     * @return if the player is online
     */
    public abstract boolean isOnline();

    /**
     * Gets the skin texture of the player
     *
     * @return the skin texture of the player
     */
    public abstract String getSkinTexture();

    /**
     * Updates what the player currently has in their cursor.
     * May not be supported on all platforms and is used due to
     * buggy behavior on some versions when cancelling events.
     */
    public abstract void updateCursor();

    /**
     * Gets the amount of inventory slots that
     * are free in the player's inventory
     *
     * @return the amount of slots that are free in the player's inventory air or null depending on version
     */
    public abstract int getOpenInventorySlots();
}