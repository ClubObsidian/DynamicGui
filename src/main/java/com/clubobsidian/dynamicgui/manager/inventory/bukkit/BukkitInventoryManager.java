/*
   Copyright 2019 Club Obsidian and contributors.

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
package com.clubobsidian.dynamicgui.manager.inventory.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.bukkit.BukkitInventoryWrapper;
import com.clubobsidian.dynamicgui.manager.inventory.InventoryManager;

public class BukkitInventoryManager extends InventoryManager {

	@Override
	public Object createInventory(int size, String title) 
	{
		return Bukkit.getServer().createInventory(null, size, title);
	}
	
	@Override
	public Object createInventory(String title, String type) 
	{
		return Bukkit.getServer().createInventory(null, InventoryType.valueOf(type), title);
	}

	@Override
	public InventoryWrapper<?> createInventoryWrapper(Object inventory) 
	{
		return new BukkitInventoryWrapper<Inventory>((Inventory) inventory);
	}
}