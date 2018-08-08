package me.virustotal.dynamicgui.inventory;

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
	
	@SuppressWarnings("unchecked")
	protected void setItemStack(Object itemStack)
	{
		this.itemStack = (T) itemStack;
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
	public abstract void removeEnchant(EnchantmentWrapper enchant);
	public abstract List<EnchantmentWrapper> getEnchants();
	
	
	public abstract String getString(String... path);
	public abstract void setString(String set, String... path);
	
	public String getString(List<String> path) 
	{
		return this.getString(path.toArray(new String[path.size()]));
	}

	public void setString(String str, List<String> path) 
	{
		this.setString(str, path.toArray(new String[path.size()]));
	}
}