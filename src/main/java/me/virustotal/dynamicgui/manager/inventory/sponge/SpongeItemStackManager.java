package me.virustotal.dynamicgui.manager.inventory.sponge;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.inventory.ItemStackWrapper;
import me.virustotal.dynamicgui.inventory.sponge.SpongeItemStackWrapper;
import me.virustotal.dynamicgui.manager.inventory.ItemStackManager;

public class SpongeItemStackManager extends ItemStackManager {

	@Override
	public Object createItemStack(String type, int quantity) 
	{
		Optional<ItemType> itemType = Sponge.getGame().getRegistry().getType(ItemType.class, type);
		if(itemType.isPresent())
		{
			return ItemStack
					.builder()
					.quantity(quantity)
					.itemType(itemType.get())
					.build();
		}
		return null;
	}

	@Override
	public ItemStackWrapper<?> createItemStackWrapper(Object itemStack) 
	{
		if(itemStack == null)
		{
			DynamicGUI.get().getLogger().info("Created null itemstack from the itemstack manager");
			return new SpongeItemStackWrapper<ItemStack>(null);
		}
		return new SpongeItemStackWrapper<ItemStack>((ItemStack) itemStack);
	}
}