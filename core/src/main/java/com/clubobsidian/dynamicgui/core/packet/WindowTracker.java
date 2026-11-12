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

package com.clubobsidian.dynamicgui.core.packet;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tracks the native window each player has open from the window packets the server sends,
 * so slot updates can be sent for the window without asking the platform for its container id.
 */
public class WindowTracker {

    private final Map<UUID, NativeWindow> windows = new ConcurrentHashMap<>();

    public void open(UUID uuid, int windowId) {
        this.windows.put(uuid, new NativeWindow(windowId, 0));
    }

    public void updateState(UUID uuid, int windowId, int stateId) {
        this.windows.computeIfPresent(uuid, (key, window) -> {
            if (window.windowId() == windowId) {
                return new NativeWindow(windowId, stateId);
            }
            return window;
        });
    }

    /**
     * Clears the tracked window if it is the window being closed
     *
     * @param uuid     the uuid of the player
     * @param windowId the window being closed
     * @return if the closed window was the tracked window
     */
    public boolean close(UUID uuid, int windowId) {
        NativeWindow window = this.windows.get(uuid);
        if (window != null && window.windowId() == windowId) {
            return this.windows.remove(uuid, window);
        }
        return false;
    }

    @Nullable
    public NativeWindow get(UUID uuid) {
        return this.windows.get(uuid);
    }

    public void remove(UUID uuid) {
        this.windows.remove(uuid);
    }

    public void clear() {
        this.windows.clear();
    }

    public record NativeWindow(int windowId, int stateId) {
    }
}
