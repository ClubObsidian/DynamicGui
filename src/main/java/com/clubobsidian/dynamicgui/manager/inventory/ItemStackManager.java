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
package com.clubobsidian.dynamicgui.manager.inventory;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.inventory.bukkit.BukkitItemStackManager;
import com.clubobsidian.dynamicgui.manager.inventory.sponge.SpongeItemStackManager;
import com.clubobsidian.dynamicgui.server.ServerType;

public abstract class ItemStackManager {

	private static ItemStackManager instance;
	
	public static ItemStackManager get()
	{
		if(instance == null)
		{
			if(ServerType.SPIGOT == DynamicGui.get().getServer().getType())
			{
				instance = new BukkitItemStackManager();
			}
			else if(ServerType.SPONGE == DynamicGui.get().getServer().getType())
			{
				instance = new SpongeItemStackManager();
			}
		}
		return instance;
	}
	
	public abstract Object createItemStack(String type, int quantity);
	public abstract ItemStackWrapper<?> createItemStackWrapper(Object itemStack);
	
	public ItemStackWrapper<?> createItemStackWrapper(String type, int quantity)
	{
		Object itemStack = this.createItemStack(type, quantity);
		return this.createItemStackWrapper(itemStack);
	}
}
