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

package com.clubobsidian.dynamicgui.parser.macro;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.parser.macro.MacroToken;
import com.clubobsidian.wrappy.ConfigurationSection;

import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleMacroToken implements MacroToken {

    /**
     *
     */
    private static final long serialVersionUID = -6384645527099128238L;

    private Map<String, Object> macros;

    public SimpleMacroToken(ConfigurationSection section) {
        this.parse(section);
    }

    private void parse(ConfigurationSection section) {
        this.macros = new LinkedHashMap<>();
        for (Object key : section.getKeys()) {
            if (key instanceof String) {
                String keyStr = (String) key;
                this.macros.put(keyStr, section.get(key));
            } else {
                DynamicGui.get().getLogger().error("Macros do not support non-string keys: " + key);
            }
        }
    }

    @Override
    public Map<String, Object> getMacros() {
        return this.macros;
    }
}