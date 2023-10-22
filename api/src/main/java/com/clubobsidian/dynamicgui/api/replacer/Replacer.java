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

package com.clubobsidian.dynamicgui.api.replacer;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;

public abstract class Replacer {

    private final String toReplace;
    private final boolean listener;

    public Replacer(String toReplace) {
        this(toReplace, false);
    }

    public Replacer(String toReplace, boolean listener) {
        this.toReplace = toReplace;
        this.listener = listener;
    }

    /**
     * Gets the string to replace
     *
     * @return The string to replace
     */
    public String getToReplace() {
        return this.toReplace;
    }

    /**
     * Replaces the toReplace string with the replacement
     * @param text
     * @param playerWrapper
     * @return
     */
    public abstract String replacement(String text, PlayerWrapper<?> playerWrapper);

    /**
     * Gets if the replacer has a listener
     *
     * @return If the replacer has a listener
     */
    public boolean hasListener() {
        return this.listener;
    }
}