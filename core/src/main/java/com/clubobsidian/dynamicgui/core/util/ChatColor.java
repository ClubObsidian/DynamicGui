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
package com.clubobsidian.dynamicgui.core.util;

import java.awt.*;

public enum ChatColor {

    AQUA('b', 85, 255, 255),
    BLACK('0', 0, 0, 0),
    BLUE('9', 85, 85, 255),
    DARK_AQUA('3', 0, 170, 170),
    DARK_BLUE('1', 0, 0, 170),
    DARK_GRAY('8', 85, 85, 85),
    DARK_GREEN('2', 0, 170, 0),
    DARK_PURPLE('5', 170, 0, 170),
    DARK_RED('4', 170, 0, 0),
    GOLD('6', 170, 170, 0),
    GRAY('7', 170, 170, 170),
    GREEN('a', 85, 255, 85),
    LIGHT_PURPLE('d', 255, 85, 255),
    RED('c', 255, 85, 85),
    WHITE('f', 255, 255, 255),
    YELLOW('e', 255, 255, 85),
    //Formatting
    BOLD('l', true),
    ITALIC('o', true),
    MAGIC('k', true),
    RESET('r', true),
    STRIKETHROUGH('m', true),
    UNDERLINE('n', true);

    public static final char FORMATTING_CODE = '\u00A7';

    private final char colorCode;
    private final boolean formatting;
    private int red;
    private int green;
    private int blue;
    private Color color;

    ChatColor(char colorCode, int red, int green, int blue) {
        this(colorCode, false);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.color = new Color(red, green, blue);
    }

    ChatColor(char colorCode, boolean formatting) {
        this.colorCode = colorCode;
        this.formatting = formatting;
    }

    public char getColorCode() {
        return this.colorCode;
    }

    public boolean isColor() {
        return !this.isFormatting();
    }

    public boolean isFormatting() {
        return this.formatting;
    }

    public int getRed() {
        return this.red;
    }

    public int getGreen() {
        return this.green;
    }

    public int getBlue() {
        return this.blue;
    }

    public Color getJavaColor() {
        return this.color;
    }

    public static String translateAlternateColorCodes(char translate, String message) {
        char[] chars = message.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == translate) {
                if (i + 1 < chars.length) {
                    for (ChatColor color : ChatColor.values()) {
                        if (chars[i + 1] == color.getColorCode()) {
                            chars[i] = ChatColor.FORMATTING_CODE;
                        }
                    }
                }
            }
        }
        return String.valueOf(chars);
    }

    public static String translateAlternateColorCodes(String message) {
        return translateAlternateColorCodes('&', message);
    }

    @Override
    public String toString() {
        return ChatColor.FORMATTING_CODE + "" + this.getColorCode();
    }
}