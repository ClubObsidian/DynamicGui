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

import com.clubobsidian.dynamicgui.core.event.inventory.Click;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow.WindowClickType;
import org.jetbrains.annotations.Nullable;

public final class PacketClickMapper {

    private PacketClickMapper() {
    }

    /**
     * Maps a window click to a click, clicks that are not supported
     * such as drags, swaps and drops are mapped to null.
     *
     * @param type   the window click type
     * @param button the button that was clicked
     * @return the click or null if the click is not supported
     */
    @Nullable
    public static Click toClick(@Nullable WindowClickType type, int button) {
        if (type == null) {
            return null;
        }
        return switch (type) {
            case PICKUP -> button == 0 ? Click.LEFT : button == 1 ? Click.RIGHT : null;
            case QUICK_MOVE -> button == 0 ? Click.SHIFT_LEFT : button == 1 ? Click.SHIFT_RIGHT : null;
            case CLONE -> Click.MIDDLE;
            default -> null;
        };
    }
}
