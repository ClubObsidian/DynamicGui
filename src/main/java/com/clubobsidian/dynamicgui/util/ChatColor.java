/*
   Copyright 2018 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.dynamicgui.util;

public enum ChatColor {

	AQUA('b'),
	BLACK('0'),
	BLUE('9'),
	DARK_AQUA('3'),
	DARK_BLUE('1'),
	DARK_GRAY('8'),
	DARK_GREEN('2'),
	DARK_PURPLE('5'),
	DARK_RED('4'),
	GOLD('6'),
	GRAY('7'),
	GREEN('a'),
	LIGHT_PURPLE('d'),
	RED('c'),
	WHITE('f'),
	YELLOW('e'),
	//Formatting
	BOLD('l'),
	ITALIC('o'),
	MAGIC('k'),
	RESET('r'),
	STRIKETHROUGH('m'),
	UNDERLINE('n');
	
	private char colorCode;
	private ChatColor(char colorCode)
	{
		this.colorCode = colorCode;
	}
	
	public char getColorCode()
	{
		return this.colorCode;
	}
	
	public static String translateAlternateColorCodes(char translate, String message)
	{
		char[] chars = message.toCharArray();
		for(int i = 0; i < chars.length; i++)
		{
			if(chars[i] == translate)
			{
				if(i + 1 < chars.length)
				{
					for(ChatColor color : ChatColor.values())
					{
						if(chars[i + 1] == color.getColorCode())
						{
							chars[i] = '\u00A7';
						}
					}
				}
			}
		}
		return String.valueOf(chars);
	}
	
	public static String translateAlternateColorCodes(String message)
	{
		return translateAlternateColorCodes('&', message);
	}
}
