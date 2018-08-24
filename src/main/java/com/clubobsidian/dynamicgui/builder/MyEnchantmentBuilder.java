package com.clubobsidian.dynamicgui.builder;

import com.clubobsidian.dynamicgui.objects.EnchantmentWrapper;

public class MyEnchantmentBuilder {
	
	private String enchantment;
	private int level;
	
	public MyEnchantmentBuilder setEnchantment(String enchantment)
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