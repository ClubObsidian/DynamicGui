/*
 *    Copyright 2021 Club Obsidian and contributors.
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
package com.clubobsidian.dynamicgui.parser.macro;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.clubobsidian.wrappy.ConfigurationSection;

public class MacroToken implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6384645527099128238L;

    private Map<String, Object> macros;

    public MacroToken(ConfigurationSection section) {
        this.parse(section);
    }

    private void parse(ConfigurationSection section) {
        this.macros = new LinkedHashMap<>();
        for (String key : section.getKeys()) {
            this.macros.put(key, section.get(key));
        }
    }

    public Map<String, Object> getMacros() {
        return this.macros;
    }
}