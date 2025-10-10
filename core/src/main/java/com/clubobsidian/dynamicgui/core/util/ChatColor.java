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

package com.clubobsidian.dynamicgui.core.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ChatColor {

    private static final char SECTION_CHAR = '§';
    private static final char AMPERSAND_CHAR = '&';

    private static final LegacyComponentSerializer AMPERSAND = LegacyComponentSerializer.builder()
            .hexColors()
            .character(AMPERSAND_CHAR)
            .build();
    private static final LegacyComponentSerializer SECTION = LegacyComponentSerializer.builder()
            .hexColors()
            .character(SECTION_CHAR)
            .build();

    public static Component toComponentAmpersand(@NotNull String componentStr) {
        Objects.requireNonNull(componentStr);
        return AMPERSAND
                .deserialize(componentStr.replace(SECTION_CHAR, AMPERSAND_CHAR))
                .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    public static String toSection(@NotNull Component component) {
        Objects.requireNonNull(component);
        return SECTION.serialize(component);
    }
}