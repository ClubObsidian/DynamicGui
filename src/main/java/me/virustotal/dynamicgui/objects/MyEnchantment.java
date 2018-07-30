package me.virustotal.dynamicgui.objects;

import java.io.Serializable;

import org.bukkit.enchantments.Enchantment;

public class MyEnchantment implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1056076873542226033L;
	private String enchant;
	private int level;
	
	public MyEnchantment(Enchantment enchant, int level)
	{
		this.enchant = enchant.getName();
		this.level = level;
	}
	
	public Enchantment getEnchant()
	{
		return Enchantment.getByName(this.enchant);
	}
	
	public int getLevel()
	{
		return this.level;
	}
}