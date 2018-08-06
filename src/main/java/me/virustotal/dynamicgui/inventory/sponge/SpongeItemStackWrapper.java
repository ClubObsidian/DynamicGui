package me.virustotal.dynamicgui.inventory.sponge;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.enchantment.Enchantment;
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
		
		// TODO Auto-generated method stub
		
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
	public void setDurability(short durability) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEnchant(EnchantmentWrapper enchant) 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void removeEnchant(EnchantmentWrapper enchant) 
	{
		// TODO Auto-generated method stub
		
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
	public String getString(String... path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setString(String str, String... path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getString(List<String> path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setString(String str, List<String> path) {
		// TODO Auto-generated method stub
		
	}

}