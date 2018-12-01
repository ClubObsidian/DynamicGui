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
package com.clubobsidian.dynamicgui.entity;

import java.util.UUID;

import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.util.Statistic;

public abstract class PlayerWrapper<T> {

	private T player;
	public PlayerWrapper(T player)
	{
		this.player = player;
	}
	
	public T getPlayer()
	{
		return this.player;
	}
	
	public abstract String getName();
	public abstract UUID getUniqueId();
	public abstract void chat(String message);
	public abstract void sendMessage(String message);
	public abstract boolean hasPermission(String permission);
	public abstract int getExperience();
	public abstract void setExperience(int experience);
	public abstract int getLevel();
	public abstract InventoryWrapper<?> getOpenInventoryWrapper();
	public abstract void closeInventory();
	public abstract void openInventory(InventoryWrapper<?> inventoryWrapper);
	public abstract void sendPluginMessage(DynamicGuiPlugin plugin, String channel, byte[] message);
	public abstract void playSound(String sound, Float volume, Float pitch);
	public abstract void playEffect(String effect, int data);
	public abstract int getStatistic(Statistic statistic);
	public abstract int getStatistic(Statistic statistic, String data);
	public abstract void updateInventory();
		
}