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

package com.clubobsidian.dynamicgui.api.manager;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.gui.Gui;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class GuiManager {

    @Inject
    private static GuiManager instance;

    public static GuiManager get() {
        return instance;
    }

    public abstract boolean isGuiLoaded(String name);

    public abstract Gui getGui(String name);

    public abstract void reloadGuis(boolean force);

    public abstract List<Gui> getGuis();

    public abstract Map<UUID, Gui> getPlayerGuis();

    public abstract boolean hasGuiCurrently(PlayerWrapper<?> playerWrapper);

    public abstract boolean hasGuiOpen(PlayerWrapper<?> playerWrapper);

    public abstract void cleanupPlayerGui(PlayerWrapper<?> playerWrapper);

    public abstract Gui getPlayerGui(PlayerWrapper<?> playerWrapper);

    public CompletableFuture<Boolean> openGui(Object player, String guiName) {
        return this.openGui(EntityManager.get().createPlayerWrapper(player), guiName);
    }

    public CompletableFuture<Boolean> openGui(Object player, Gui gui) {
        return this.openGui(EntityManager.get().createPlayerWrapper(player), gui);
    }

    public CompletableFuture<Boolean> openGui(PlayerWrapper<?> playerWrapper, String guiName) {
        return this.openGui(playerWrapper, guiName, null);
    }

    public CompletableFuture<Boolean> openGui(PlayerWrapper<?> playerWrapper, String guiName, Gui back) {
        return this.openGui(playerWrapper, this.getGui(guiName), back);
    }

    public CompletableFuture<Boolean> openGui(PlayerWrapper<?> playerWrapper, Gui gui) {
        return this.openGui(playerWrapper, gui, null);
    }

    public abstract CompletableFuture<Boolean> openGui(PlayerWrapper<?> playerWrapper, Gui gui, Gui back);

}