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
package com.clubobsidian.dynamicgui.manager.inventory.sponge;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.inventory.sponge.SpongeItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.inventory.ItemStackManager;

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
			DynamicGui.get().getLogger().info("Created null itemstack from the itemstack manager");
			return new SpongeItemStackWrapper<ItemStack>(null);
		}
		return new SpongeItemStackWrapper<ItemStack>((ItemStack) itemStack);
	}
}
