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

package com.clubobsidian.dynamicgui.parser.index;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class SlotExpander {

    private static boolean canBeExpanded(String index) {
        return ((index.contains(",") || index.contains("-")) && NumberUtils.isDigits(index
                .replace(",", "")
                .replace("-", "")));
    }

    public static boolean isSlot(@NotNull Object slot) {
        if (slot instanceof String) {
            String index = (String) slot;
            return NumberUtils.isDigits(index) || canBeExpanded(index);
        } else if (slot instanceof Integer) {
            return true;
        }
        return false;
    }

    public static List<Integer> expand(@NotNull Object index) {
        Objects.requireNonNull(index, "index can not be null");
        List<Integer> slots = new ArrayList<>();
        if (index instanceof Integer) {
            slots.add((Integer) index);
        } else if (index instanceof String) {
            String expand = (String) index;
            String[] commaSplit = expand.contains(",") ? expand.split(",") : new String[]{expand};
            for (String comma : commaSplit) {
                if (comma.contains("-")) {
                    System.out.println(comma);
                    String[] dashSplit = comma.split("-");
                    if (dashSplit.length != 2) {
                        DynamicGui.get().getLogger().error("Invalid range pattern '%s'", expand);
                    }
                    for (int i = Integer.parseInt(dashSplit[0]); i < Integer.parseInt(dashSplit[1]) + 1; i++) {
                        slots.add(i);
                    }
                } else {
                    slots.add(Integer.parseInt(comma));
                }
            }
        }
        Collections.sort(slots);
        return slots;
    }

    private SlotExpander() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }
}