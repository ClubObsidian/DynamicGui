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
		return message.replace(translate, '\u00A7');
	}
	
	public static String translateAlternateColorCodes(String message)
	{
		return translateAlternateColorCodes('&', message);
	}
}