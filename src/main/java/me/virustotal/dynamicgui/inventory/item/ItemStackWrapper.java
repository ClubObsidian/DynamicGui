package me.virustotal.dynamicgui.inventory.item;

import java.io.Serializable;
import java.util.List;

import me.virustotal.dynamicgui.objects.EnchantmentWrapper;

public abstract class ItemStackWrapper<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7902733103453967016L;
	
	private T itemStack;
	public ItemStackWrapper(T itemStack)
	{
		this.itemStack = itemStack;
	}
	
	public T getItemStack()
	{
		return this.itemStack;
	}

	public abstract int getAmount();
	
	public abstract String getType();
	public abstract void setType(String type);
	
	public abstract String getName();
	public abstract void setName(String name);
	
	public abstract List<String> getLore();
	public abstract void setLore(List<String> lore);
	
	public abstract short getDurability();
	public abstract void setDurability(short durability);
	
	public abstract void addEnchant(EnchantmentWrapper enchant);
	public abstract List<EnchantmentWrapper> getEnchants();
}