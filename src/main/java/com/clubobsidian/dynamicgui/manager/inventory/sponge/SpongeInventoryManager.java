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

import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.sponge.SpongeInventoryWrapper;
import com.clubobsidian.dynamicgui.manager.inventory.InventoryManager;

public class SpongeInventoryManager extends InventoryManager {

	@Override
	public Object createInventory(int size, String title) 
	{
		return Inventory.builder()
				.of(InventoryArchetypes.DOUBLE_CHEST)
				.property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of(title)))
				.property(InventoryDimension.PROPERTY_NAME, new InventoryDimension(9, size / 9))
				.build(DynamicGui.get().getPlugin());
	}

	@Override
	public InventoryWrapper<?> createInventoryWrapper(Object inventory) 
	{
		return new SpongeInventoryWrapper<Inventory>((Inventory) inventory);
	}
}