/*
   Copyright 2018 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.dynamicgui.inventory.sponge;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.enchantment.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

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
	public void setType(String type) 
	{
		Optional<ItemType> itemType = Sponge.getGame().getRegistry().getType(ItemType.class, type);
		if(itemType.isPresent())
		{
			ItemType itemStackType = itemType.get();
			DynamicGui.get().getLogger().info("type: " + type + " is null: " + itemStackType);
			ItemStack itemStack = ItemStack.builder()
					.itemType(itemStackType)
					.from(this.getItemStack())
					.itemType(itemStackType)
					.build();
			this.setItemStack(itemStack);
			DynamicGui.get().getLogger().info("This itemstack is null or is it: " + itemStack.toString());
		}
		DynamicGui.get().getLogger().info("From setType, itemstack: " + this.getItemStack().toString());
		DynamicGui.get().getLogger().info("Item type is present: " + itemType.isPresent());
	}

	@Override
	public int getAmount() 
	{
		return this.getItemStack().getQuantity();
	}

	@Override
	public String getName() 
	{
		Optional<Text> name = this.getItemStack().get(Keys.DISPLAY_NAME);
		if(name.isPresent())
		{
			return name.get().toPlain();
		}
		return "";
	}

	@Override
	public void setName(String name) 
	{
		this.getItemStack().offer(Keys.DISPLAY_NAME, Text.of(name));
	}

	@Override
	public List<String> getLore() 
	{
		List<String> lore = new ArrayList<>();
		Optional<List<Text>> itemLore = this.getItemStack().get(Keys.ITEM_LORE);
		if(itemLore.isPresent())
		{
			for(Text text : itemLore.get())
			{
				lore.add(text.toPlain());
			}
		}
		return lore;
	}

	@Override
	public void setLore(List<String> lore) 
	{
		List<Text> textLore = new ArrayList<>();
		for(String l : lore)
		{
			textLore.add(Text.of(l));
		}
		this.getItemStack().offer(Keys.ITEM_LORE, textLore);
	}

	@Override
	public short getDurability() 
	{
		Optional<Integer> itemDurability = this.getItemStack().get(Keys.ITEM_DURABILITY);
		if(itemDurability.isPresent())
		{
			int dura = itemDurability.get();
			if(dura <= Short.MAX_VALUE)
			{
				return (short) dura;
			}
		}
		return 0;
	}

	@Override
	public void setDurability(short durability) 
	{
		this.getItemStack().offer(Keys.ITEM_DURABILITY, (int) durability);
	}

	@Override
	public void addEnchant(EnchantmentWrapper enchant) 
	{
		List<Enchantment> enchants = new ArrayList<>();
		Optional<List<Enchantment>> itemEnchants = this.getItemStack().get(Keys.ITEM_ENCHANTMENTS);
		if(itemEnchants.isPresent())
		{
			enchants = itemEnchants.get();
		}
		Optional<EnchantmentType> enchantmentType = Sponge.getGame().getRegistry().getType(EnchantmentType.class, enchant.getEnchant());
		if(enchantmentType.isPresent())
		{
			enchants.add(Enchantment.builder().type(enchantmentType.get()).level(enchant.getLevel()).build());
		}
		
		if(enchants.size() > 0)
		{
			this.getItemStack().offer(Keys.ITEM_ENCHANTMENTS, enchants);
		}
	}
	
	@Override
	public void removeEnchant(EnchantmentWrapper enchant) 
	{
		List<Enchantment> enchants = new ArrayList<>();
		Optional<List<Enchantment>> itemEnchants = this.getItemStack().get(Keys.ITEM_ENCHANTMENTS);
		if(itemEnchants.isPresent())
		{
			enchants = itemEnchants.get();
		}
		Optional<EnchantmentType> enchantmentType = Sponge.getGame().getRegistry().getType(EnchantmentType.class, enchant.getEnchant());
		if(enchantmentType.isPresent())
		{
			for(Enchantment ench : enchants)
			{
				if(ench.getType().equals(enchantmentType.get()))
				{
					enchants.remove(ench);
					break;
				}
			}
		}
		this.getItemStack().offer(Keys.ITEM_ENCHANTMENTS, enchants);
	}

	@Override
	public List<EnchantmentWrapper> getEnchants() 
	{
		List<EnchantmentWrapper> enchants = new ArrayList<>();
		Optional<List<Enchantment>> itemEnchants = this.getItemStack().get(Keys.ITEM_ENCHANTMENTS);
		if(itemEnchants.isPresent())
		{
			for(Enchantment ench : itemEnchants.get())
			{
				enchants.add(new EnchantmentWrapper(ench.getType().getId(), ench.getLevel()));
			}
		}
		return enchants;
	}

	@Override
	public void setNBT(String nbt) 
	{
		
	}
}
