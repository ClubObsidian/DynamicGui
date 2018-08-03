package me.virustotal.dynamicgui.objects;

import java.io.Serializable;

public class EnchantmentWrapper implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1056076873542226033L;
	private String enchant;
	private int level;
	
	public EnchantmentWrapper(String enchant, int level)
	{
		this.enchant = enchant;
		this.level = level;
	}
	
	public String getEnchant()
	{
		return this.enchant;
	}
	
	public int getLevel()
	{
		return this.level;
	}
}