package me.virustotal.dynamicgui.api;

import org.bukkit.enchantments.Enchantment;

import me.virustotal.dynamicgui.objects.EnchantmentWrapper;

public class MyEnchantmentBuilder {
	
	private Enchantment enchantment;
	private int level;
	
	public MyEnchantmentBuilder setEnchantment(Enchantment enchantment)
	{
		this.enchantment = enchantment;
		return this;
	}
	
	public MyEnchantmentBuilder setLevel(int level)
	{
		this.level = level;
		return this;
	}
	
	
	public EnchantmentWrapper build()
	{
		return new EnchantmentWrapper(this.enchantment, level);
	}
}