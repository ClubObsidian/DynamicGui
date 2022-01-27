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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public class MacroParser implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4558006309742656177L;

    private final List<MacroToken> tokens;

    public MacroParser(List<MacroToken> tokens) {
        this.tokens = tokens;
    }

    public List<MacroToken> getTokens() {
        return Collections.unmodifiableList(this.tokens);
    }

    public String parseStringMacros(final String replaceIn) {
        if (replaceIn == null) {
            return null;
        }

        String replace = replaceIn;
        for (MacroToken token : this.tokens) {
            for (Entry<String, Object> next : token.getMacros().entrySet()) {
                String key = next.getKey();
                Object value = next.getValue();

                if (replace.contains(key)) {
                    replace = StringUtils.replace(replace, key, value.toString());
                }
            }
        }

        for (MacroToken token : this.tokens) {
            for (Entry<String, Object> entry : token.getMacros().entrySet()) {
                String key = entry.getKey();
                if (replace.contains(key)) {
                    return this.parseStringMacros(replace);
                }
            }
        }

        return replace;
    }

    @SuppressWarnings("unchecked")
    public List<String> parseListMacros(final List<String> replaceIn) {
        List<String> newList = new ArrayList<>(replaceIn);


        for (MacroToken token : this.tokens) {
            for (Entry<String, Object> next : token.getMacros().entrySet()) {
                String key = next.getKey();

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
            for (Entry<String, Object> entry : token.getMacros().entrySet()) {
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
}