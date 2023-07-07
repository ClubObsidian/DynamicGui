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

package com.clubobsidian.dynamicgui.core.util;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ChatColor {

    public static final char SECTION_CODE = '\u00A7';

    private static final LegacyComponentSerializer AMPERSAND = LegacyComponentSerializer.builder()
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .character('&')
            .build();
    private static final LegacyComponentSerializer SECTION = LegacyComponentSerializer.builder()
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .character(SECTION_CODE)
            .build();

    public static String translateAlternateColorCodes(String message) {
        return SECTION.serialize(AMPERSAND.deserialize(message));
    }

    public static String stripColor(String message) {
        return SECTION.deserialize(SECTION.serialize(AMPERSAND.deserialize(message))).content();
    }
}