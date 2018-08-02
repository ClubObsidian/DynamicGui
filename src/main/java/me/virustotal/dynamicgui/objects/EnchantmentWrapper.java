package me.virustotal.dynamicgui.objects;

import java.io.Serializable;

import org.bukkit.enchantments.Enchantment;

public class EnchantmentWrapper implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1056076873542226033L;
	private String enchant;
	private int level;
	
	public EnchantmentWrapper(Enchantment enchant, int level)
	{
		this.enchant = enchant.getName();
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