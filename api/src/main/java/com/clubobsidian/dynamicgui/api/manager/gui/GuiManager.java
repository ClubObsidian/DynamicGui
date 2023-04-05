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

package com.clubobsidian.dynamicgui.api.manager.gui;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.manager.entity.EntityManager;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class GuiManager {

    @Inject
    private static GuiManager instance;

    public static GuiManager get() {
        if (instance != null) {
            instance.initialize();
        }
        return instance;
    }

    /**
     * Initializes the GuiManager
     *
     * @return whether the GuiManager was initialized
     */
    public abstract boolean initialize();

    /**
     * Checks if a gui is loaded
     *
     * @param name the name of the gui
     * @return if the gui is loaded
     */
    public abstract boolean isGuiLoaded(String name);

    /**
     * Gets a gui by nmae
     *
     * @param name the name of the gui
     * @return a gui or null if it does not exist
     */
    @Nullable
    public abstract Gui getGui(String name);

    /**
     * Reloads the guis, if force is true then
     * the GuiManager will not use any caching
     * and will do a full refresh of the guis.
     *
     * @param force if the gui should be force reloaded
     */
    public abstract void reloadGuis(boolean force);

    /**
     * Gets an unmodifiable list of all loaded guis.
     *
     * @return list of loaded guis
     */
    @Unmodifiable
    public abstract List<Gui> getGuis();

    /**
     * Gets an unmodifiable map of all open
     * player guis.
     *
     * @return map of player guis
     */
    @Unmodifiable
    public Map<UUID, Gui> getPlayerGuis() {
        return Collections.unmodifiableMap(this.getPlayerGuisInternal());
    }

    @ApiStatus.Internal
    protected abstract Map<UUID, Gui> getPlayerGuisInternal();


    /**
     * Gets if a player has a gui in the cache, the user may not have a gui
     * open.
     *
     * @param playerWrapper the player wrapper to check
     * @return if the player has a gui in the cache
     */
    public boolean hasGuiCurrently(PlayerWrapper<?> playerWrapper) {
        return this.getPlayerGuisInternal().get(playerWrapper.getUniqueId()) != null;
    }

    /**
     * Gets if a player has a gui in the cache and has a gui open.
     *
     * @param playerWrapper the player wrapper to check
     * @return if the player has a gui in the cache and has a gui open
     */
    public boolean hasGuiOpen(PlayerWrapper<?> playerWrapper) {
        if (playerWrapper.getOpenInventoryWrapper() == null) {
            return false;
        }
        return this.hasGuiCurrently(playerWrapper);
    }

    /**
     * Cleans up a player's gui data from the cache.
     *
     * @param playerWrapper
     */
    public void cleanupPlayerGui(PlayerWrapper<?> playerWrapper) {
        this.getPlayerGuisInternal().remove(playerWrapper.getUniqueId());
    }

    /**
     * Gets the current gui if any for a given player or null.
     *
     * @param playerWrapper the player to check
     * @return the gui the player has in the cache or null
     */
    @Nullable
    public Gui getPlayerGui(PlayerWrapper<?> playerWrapper) {
        return this.getPlayerGuisInternal().get(playerWrapper.getUniqueId());
    }

    /**
     * Opens a gui for a player.
     *
     * @param player  native player object to open the gui for
     * @param guiName name of the gui to open
     * @return a boolean future that returns true if the gui was opened
     */
    public CompletableFuture<Boolean> openGui(Object player, String guiName) {
        return this.openGui(EntityManager.get().createPlayerWrapper(player), guiName);
    }

    /**
     * Opens a gui for a player.
     *
     * @param player native player object to open the gui for
     * @param gui    the gui to open
     * @return a boolean future that returns true if the gui was opened
     */
    public CompletableFuture<Boolean> openGui(Object player, Gui gui) {
        return this.openGui(EntityManager.get().createPlayerWrapper(player), gui);
    }

    /**
     * Opens a gui for a player.
     *
     * @param playerWrapper player wrapper to open the gui for
     * @param guiName       name of the gui to open
     * @return a boolean future that returns true if the gui was opened
     */
    public CompletableFuture<Boolean> openGui(PlayerWrapper<?> playerWrapper, String guiName) {
        return this.openGui(playerWrapper, guiName, null);
    }

    /**
     * Opens a gui for a player.
     *
     * @param playerWrapper player wrapper to open the gui for
     * @param guiName       name of the gui to open
     * @param back          the gui to set as a back gui
     * @return a boolean future that returns true if the gui was opened
     */
    public CompletableFuture<Boolean> openGui(PlayerWrapper<?> playerWrapper, String guiName, Gui back) {
        return this.openGui(playerWrapper, this.getGui(guiName), back);
    }

    /**
     * Opens a gui for a player.
     *
     * @param playerWrapper player wrapper to open the gui for
     * @param gui           the gui to open
     * @return a boolean future that returns true if the gui was opened
     */
    public CompletableFuture<Boolean> openGui(PlayerWrapper<?> playerWrapper, Gui gui) {
        return this.openGui(playerWrapper, gui, null);
    }

    /**
     * Opens a gui for a player.
     *
     * @param playerWrapper player wrapper to open the gui for
     * @param gui           the gui to open
     * @param back          the gui to set as a back gui
     * @return a boolean future that returns true if the gui was opened
     */
    public abstract CompletableFuture<Boolean> openGui(PlayerWrapper<?> playerWrapper, Gui gui, Gui back);

}