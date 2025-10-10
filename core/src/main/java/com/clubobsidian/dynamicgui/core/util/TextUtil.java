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
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.kyori.adventure.text.format.TextDecoration.ITALIC;
import static net.kyori.adventure.text.format.TextDecoration.State.FALSE;

public final class TextUtil {

    private static final char SECTION_CHAR = '§';
    private static final char AMPERSAND_CHAR = '&';
    private static final LegacyComponentSerializer SECTION = LegacyComponentSerializer.builder()
            .hexColors()
            .character(SECTION_CHAR)
            .build();
    private static final MiniMessage MINI = MiniMessage
            .builder()
            .postProcessor((component -> component.decorationIfAbsent(ITALIC, FALSE)))
            .strict(false)
            .build();
    private static final Pattern HEX_REGEX = Pattern.compile("&#([a-fA-F0-9]{6})");
    private static final Map<String, String> COLOR_AND_STYLE = new HashMap<>();

    static {
            COLOR_AND_STYLE.put("&0", "<black>");
            COLOR_AND_STYLE.put("&1", "<dark_blue>");
            COLOR_AND_STYLE.put("&2", "<dark_green>");
            COLOR_AND_STYLE.put("&3", "<dark_aqua>");
            COLOR_AND_STYLE.put("&4", "<dark_red>");
            COLOR_AND_STYLE.put("&5", "<dark_purple>");
            COLOR_AND_STYLE.put("&6", "<gold>");
            COLOR_AND_STYLE.put("&7", "<gray>");
            COLOR_AND_STYLE.put("&8", "<dark_gray>");
            COLOR_AND_STYLE.put("&9", "<blue>");
            COLOR_AND_STYLE.put("&a", "<green>");
            COLOR_AND_STYLE.put("&b", "<aqua>");
            COLOR_AND_STYLE.put("&c", "<red>");
            COLOR_AND_STYLE.put("&d", "<light_purple>");
            COLOR_AND_STYLE.put("&e", "<yellow>");
            COLOR_AND_STYLE.put("&f", "<white>");
            COLOR_AND_STYLE.put("&k", "<obf>");
            COLOR_AND_STYLE.put("&l", "<b>");
            COLOR_AND_STYLE.put("&m", "<st>");
            COLOR_AND_STYLE.put("&n", "<u>");
            COLOR_AND_STYLE.put("&o", "<i>");
            COLOR_AND_STYLE.put("&r", "<reset>");
    }

    public static Component toComponent(@NotNull String componentStr) {
        Objects.requireNonNull(componentStr);
        String miniString = componentStr.replace(SECTION_CHAR, AMPERSAND_CHAR);
        Matcher matcher = HEX_REGEX.matcher(miniString);
        miniString = matcher.replaceAll(m -> {
            String hex = m.group(1).toLowerCase();
            return "<#" + hex + ">";
        });
        for (Map.Entry<String, String> entry : COLOR_AND_STYLE.entrySet()) {
            miniString = StringUtils.replace(miniString, entry.getKey(), entry.getValue());
        }
        return MINI.deserialize(miniString);
    }

    public static String toSection(@NotNull Component component) {
        Objects.requireNonNull(component);
        return SECTION.serialize(component);
    }

    private TextUtil() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }
}