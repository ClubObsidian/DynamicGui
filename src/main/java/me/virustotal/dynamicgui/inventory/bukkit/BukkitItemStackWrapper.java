package me.virustotal.dynamicgui.inventory.bukkit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.virustotal.dynamicgui.inventory.ItemStackWrapper;
import me.virustotal.dynamicgui.objects.EnchantmentWrapper;

public class BukkitItemStackWrapper<T extends ItemStack> extends ItemStackWrapper<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3542885060265738780L;

	public BukkitItemStackWrapper(T itemStack) 
	{
		super(itemStack);
	}

	@Override
	public int getAmount() 
	{
		return this.getItemStack().getAmount();
	}
	
	@Override
	public String getType() 
	{
		return this.getItemStack().getType().toString();
	}

	@Override
	public void setType(String type) 
	{
		this.getItemStack().setType(Material.valueOf(type));
	}

	@Override
	public String getName() 
	{
		return this.getItemStack().getItemMeta().getDisplayName();
	}

	@Override
	public void setName(String name) 
	{
		ItemMeta itemMeta = this.getItemStack().getItemMeta();
		itemMeta.setDisplayName(name);
		this.getItemStack().setItemMeta(itemMeta);
	}

	@Override
	public List<String> getLore() 
	{
		ItemMeta itemMeta = this.getItemStack().getItemMeta();
		return itemMeta.getLore();
	}

	@Override
	public void setLore(List<String> lore) 
	{
		ItemMeta itemMeta = this.getItemStack().getItemMeta();
		itemMeta.setLore(lore);
		this.getItemStack().setItemMeta(itemMeta);
	}

	@Override
	public short getDurability() 
	{
		return this.getItemStack().getDurability();
	}

	@Override
	public void setDurability(short durability) 
	{
		this.getItemStack().setDurability(durability);
	}

	@Override
	public void addEnchant(EnchantmentWrapper enchant) 
	{
		ItemMeta itemMeta = this.getItemStack().getItemMeta();
		itemMeta.addEnchant(Enchantment.getByName(enchant.getEnchant()), enchant.getLevel(), true);
		this.getItemStack().setItemMeta(itemMeta);
	}

	@Override
	public void removeEnchant(EnchantmentWrapper enchant)
	{
		ItemMeta itemMeta = this.getItemStack().getItemMeta();
		itemMeta.removeEnchant(Enchantment.getByName(enchant.getEnchant()));
		this.getItemStack().setItemMeta(itemMeta);
	}
	
	@Override
	public List<EnchantmentWrapper> getEnchants() 
	{
		List<EnchantmentWrapper> enchants = new ArrayList<>();
		Iterator<Entry<Enchantment,Integer>> it = this.getItemStack().getItemMeta().getEnchants().entrySet().iterator();
		while(it.hasNext())
		{
			Entry<Enchantment,Integer> next = it.next();
			enchants.add(new EnchantmentWrapper(next.getKey().getName(), next.getValue()));
		}
		return enchants;
	}
}