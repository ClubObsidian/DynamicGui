package me.virustotal.dynamicgui.inventory.item.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;
import me.virustotal.dynamicgui.nbt.NBTCompound;
import me.virustotal.dynamicgui.nbt.NBTItem;
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

	@Override
	public String getString(String... path) 
	{
		NBTCompound compound = this.getCompoundFromPath(path);
		return compound.getString(path[path.length - 1]);
	}

	@Override
	public void setString(String str, String... path) 
	{
		NBTCompound compound = this.getCompoundFromPath(path);
		compound.setString(path[path.length - 1], str);
		this.setItemStack(compound.getItem());
	}

	@Override
	public String getString(List<String> path) 
	{
		return this.getString(path.toArray(new String[path.size()]));
	}

	@Override
	public void setString(String str, List<String> path) 
	{
		this.setString(str, path.toArray(new String[path.size()]));
	}
	
	private NBTCompound getCompoundFromPath(String[] path)
	{
		NBTCompound compound = new NBTItem(this.getItemStack());
		if(path.length == 1)
		{
			return compound;
		}
		
		for(int i = 1; i < path.length - 1; i++)
		{
			if(compound == null)
				return null;
			compound = compound.getCompound(path[i]);
		}
		return compound;
	}
}