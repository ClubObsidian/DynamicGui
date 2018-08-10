package me.virustotal.dynamicgui.inventory.sponge;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import me.virustotal.dynamicgui.inventory.ItemStackWrapper;
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
	public void setType(String type) 
	{
		//TODO		
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
}