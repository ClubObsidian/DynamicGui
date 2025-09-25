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

import com.clubobsidian.dynamicgui.api.parser.macro.MacroParser;
import com.clubobsidian.dynamicgui.api.parser.macro.MacroToken;
import com.clubobsidian.wrappy.ConfigurationSection;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SimpleMacroParser implements MacroParser {

    /**
     *
     */
    private static final long serialVersionUID = -4558006309742656177L;

    private final List<MacroToken> tokens;

    public SimpleMacroParser(List<MacroToken> tokens) {
        this.tokens = tokens;
    }

    @Override
    public List<MacroToken> getTokens() {
        return Collections.unmodifiableList(this.tokens);
    }

    @Override
    public String parseStringMacros(final String replaceIn) {
        if (replaceIn == null) {
            return null;
        }
        String replace = replaceIn;
        for (MacroToken token : this.tokens) {
            for (Map.Entry<String, Object> entry : token.getMacros().entrySet()) {
                if (entry.getValue() instanceof ConfigurationSection) {
                    continue;
                }
                String key = entry.getKey();
                Object value = entry.getValue();
                if (replace.contains(key)) {
                    replace = StringUtils.replace(replace, key, value.toString());
                }
            }
        }

        for (MacroToken token : this.tokens) {
            for (Map.Entry<String, Object> entry : token.getMacros().entrySet()) {
                if (entry.getValue() instanceof ConfigurationSection) {
                    continue;
                }
                String key = entry.getKey();
                if (replace.contains(key)) {
                    return this.parseStringMacros(replace);
                }
            }
        }
        return replace;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> parseListMacros(final List<String> replaceIn) {
        List<String> newList = new ArrayList<>(replaceIn);
        for (MacroToken token : this.tokens) {
            for (Map.Entry<String, Object> next : token.getMacros().entrySet()) {
                String key = next.getKey();
                if (next.getValue() instanceof ConfigurationSection) {
                    continue;
                }
                for (int i = 0; i < newList.size(); i++) {
                    String line = newList.get(i);
                    if (line.contains(key)) {
                        Object value = next.getValue();
                        if (!(value instanceof List)) {
                            String stringMacro = value.toString();
                            newList.remove(i);
                            newList.add(i, StringUtils.replace(line, key, stringMacro));

                        } else {
                            List<String> listMacro = (List<String>) value;

                            int startIndex = line.indexOf(key);
                            int endIndex = startIndex + key.length();

                            String macro = listMacro.get(0);
                            String firstLine = line.substring(0, endIndex);
                            firstLine = StringUtils.replace(firstLine, key, macro);


                            newList.remove(i);

                            newList.add(i, firstLine);

                            String ending = line.substring(endIndex);
                            String appended = ending;
                            if (listMacro.size() >= 2) {
                                appended = listMacro.get(1) + ending;
                            }

                            if (appended.length() > 0) {
                                i++;
                                newList.add(i, appended);
                            }

                            for (int j = 2; j < listMacro.size(); j++) {
                                i++;
                                newList.add(i, listMacro.get(j));
                            }
                        }
                    }
                }
            }
        }

        for (MacroToken token : this.tokens) {
            for (Map.Entry<String, Object> entry : token.getMacros().entrySet()) {
                String key = entry.getKey();
                for (String line : newList) {
                    if (line.contains(key)) {
                        return this.parseListMacros(newList);
                    }
                }
            }
        }

        return newList;
    }

    @Override
    public ConfigurationSection parseSectionMacros(ConfigurationSection copyInto) {
        for (MacroToken token : this.tokens) {
            for (Map.Entry<String, Object> entry : token.getMacros().entrySet()) {
                if (entry.getValue() instanceof ConfigurationSection tokenSection) {
                    String macroName = entry.getKey();
                    this.recurReplaceSection(macroName, tokenSection, copyInto);
                }
            }
        }
        return copyInto;
    }

    private void recurReplaceSection(String macroName,
                                     ConfigurationSection tokenSection,
                                     ConfigurationSection copyInto) {
        for (Object key : copyInto.getKeys()) {
            Object value = copyInto.get(key);
            if (value instanceof String strValue) {
                if (strValue.equals(macroName)) {
                    copyInto.set(key, null);
                    ConfigurationSection valueSection = copyInto.getConfigurationSection(key);
                    valueSection.combine(tokenSection);
                }
            }
            ConfigurationSection childSection = copyInto.getConfigurationSection(key);
            if (!childSection.getKeys().isEmpty()) {
                this.recurReplaceSection(macroName, tokenSection, childSection);
            }
        }
    }
}
