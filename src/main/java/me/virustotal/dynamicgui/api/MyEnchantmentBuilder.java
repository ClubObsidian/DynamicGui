package me.virustotal.dynamicgui.api;

import org.bukkit.enchantments.Enchantment;

import me.virustotal.dynamicgui.objects.MyEnchantment;

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
	
	
	public MyEnchantment build()
	{
		return new MyEnchantment(this.enchantment, level);
	}
}