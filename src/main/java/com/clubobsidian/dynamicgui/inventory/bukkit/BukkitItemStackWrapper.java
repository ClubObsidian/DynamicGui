/*
 *    Copyright 2021 Club Obsidian and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.clubobsidian.dynamicgui.inventory.bukkit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.clubobsidian.dynamicgui.enchantment.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.material.MaterialManager;
import com.clubobsidian.dynamicgui.util.bukkit.BukkitNBTUtil;

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
	public void setAmount(int amount)
	{
		this.getItemStack().setAmount(amount);
	}
	
	@Override
	public int getMaxStackSize() 
	{
		return this.getItemStack().getMaxStackSize();
	}
	
	@Override
	public String getType() 
	{
		return this.getItemStack().getType().toString();
	}

	@Override
	public boolean setType(final String type) 
	{
		if(type == null)
		{
			return false;
		}
		
		String normalizedType = MaterialManager.get().normalizeMaterial(type);
		Material mat = null;
		
		try
		{
			mat = Material.valueOf(normalizedType);
		}
		catch(Exception ex)
		{
			return false;
		}
		
		this.getItemStack().setType(mat);
		return true;
	}

	@Override
	public String getName() 
	{
		ItemMeta itemMeta = this.getItemStack().getItemMeta();
		if(itemMeta.hasDisplayName())
		{
			return itemMeta.getDisplayName();
		}
		return null;
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
		if(!itemMeta.hasLore())
		{
			return new ArrayList<>();
		}
		
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
		ItemMeta itemMeta = this.getItemStack().getItemMeta();
		if(itemMeta.hasEnchants())
		{
			Iterator<Entry<Enchantment,Integer>> it = itemMeta.getEnchants().entrySet().iterator();
			while(it.hasNext())
			{
				Entry<Enchantment,Integer> next = it.next();
				enchants.add(new EnchantmentWrapper(next.getKey().getName(), next.getValue()));
			}
		}
		return enchants;
	}

	@Override
	public String getNBT() 
	{
		return BukkitNBTUtil.getTag(this.getItemStack());
	}
	
	@Override
	public void setNBT(String nbt) 
	{
		ItemStack oldItemStack = this.getItemStack();
		ItemStack newItemStack = BukkitNBTUtil.setTag(this.getItemStack(), nbt);
		
		if(oldItemStack.hasItemMeta())
		{
			ItemMeta meta = oldItemStack.getItemMeta();
			ItemMeta newMeta = newItemStack.getItemMeta();
			if(meta.hasDisplayName())
			{
				newMeta.setDisplayName(meta.getDisplayName());
			}
			
			if(meta.hasEnchants())
			{
				Iterator<Entry<Enchantment,Integer>> it = meta.getEnchants().entrySet().iterator();
				while(it.hasNext())
				{
					Entry<Enchantment,Integer> next = it.next();
					newMeta.addEnchant(next.getKey(), next.getValue(), true);
				}
			}
			if(meta.hasLore())
			{
				newMeta.setLore(meta.getLore());
			}
			
			for(ItemFlag flag : meta.getItemFlags())
			{
				newMeta.addItemFlags(flag);
			}
			
			newItemStack.setItemMeta(newMeta);
		}
		
		this.setItemStack(newItemStack);
	}

	@Override
	public void setGlowing(boolean glowing) 
	{
		ItemStack item = this.getItemStack();
		ItemMeta meta = item.getItemMeta();
		if(glowing)
		{
			meta.addEnchant(Enchantment.DIG_SPEED, -1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		else
		{
			meta.removeEnchant(Enchantment.DIG_SPEED);
			meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
			
		}
		
		item.setItemMeta(meta);
	}

	@Override
	public boolean isSimilar(ItemStackWrapper<?> compareTo) 
	{
		return this.getItemStack().isSimilar((ItemStack) compareTo.getItemStack());
	}
}