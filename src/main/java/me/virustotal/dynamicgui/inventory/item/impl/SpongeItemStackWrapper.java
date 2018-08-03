package me.virustotal.dynamicgui.inventory.item.impl;

import java.util.List;

import org.spongepowered.api.item.inventory.ItemStack;

import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;
import me.virustotal.dynamicgui.objects.EnchantmentWrapper;

public class SpongeItemStackWrapper<T extends ItemStack> extends ItemStackWrapper<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4740479272146276094L;

	public SpongeItemStackWrapper(T itemStack) 
	{
		super(itemStack);
	}

	@Override
	public String getType() 
	{
		return this.getItemStack().getType().getId(); //TODO - Translate to enum like in bukkit
	}

	@Override
	public int getAmount() 
	{
		return this.getItemStack().getQuantity();
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getLore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLore(List<String> lore) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public short getDurability() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDurability(short durability) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEnchant(EnchantmentWrapper enchant) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EnchantmentWrapper> getEnchants() {
		// TODO Auto-generated method stub
		return null;
	}
}