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
package com.clubobsidian.dynamicgui.entity.bukkit;

import java.util.UUID;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.bukkit.BukkitInventoryWrapper;
import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.util.Statistic;
import com.clubobsidian.dynamicgui.util.Statistic.StatisticType;

public class BukkitPlayerWrapper<T extends Player> extends PlayerWrapper<T> {

	public BukkitPlayerWrapper(T player) 
	{
		super(player);
	}

	@Override
	public String getName() 
	{
		return this.getPlayer().getName();
	}
	
	@Override
	public UUID getUniqueId() 
	{
		return this.getPlayer().getUniqueId();
	}

	@Override
	public void chat(String message) 
	{
		this.getPlayer().chat(message);
	}

	@Override
	public void sendMessage(String message) 
	{
		this.getPlayer().sendMessage(message);
	}

	@Override
	public boolean hasPermission(String permission) 
	{
		return this.getPlayer().hasPermission(permission);
	}

	@Override
	public int getExperience() 
	{
		System.out.println("Total exp: " + this.getPlayer().getTotalExperience());
		return this.getPlayer().getTotalExperience();
	}

	@Override
	public void setExperience(int experience) 
	{
		this.getPlayer().setTotalExperience(experience);
	}

	@Override
	public int getLevel() 
	{
		return this.getPlayer().getLevel();
	}
	
	@Override
	public InventoryWrapper<Inventory> getOpenInventoryWrapper() 
	{
		InventoryView openInventory = this.getPlayer().getOpenInventory();
		if(openInventory == null)
		{
			return null;
		}
		return new BukkitInventoryWrapper<Inventory>(openInventory.getTopInventory());
	}

	@Override
	public void closeInventory() 
	{
		if(this.getPlayer().getOpenInventory() != null)
		{
			this.getPlayer().getOpenInventory().close();
		}
	}

	@Override
	public void openInventory(InventoryWrapper<?> inventoryWrapper) 
	{
		Object inventory = inventoryWrapper.getInventory();
		if(inventory instanceof Inventory)
		{
			this.getPlayer().openInventory((Inventory) inventory);
		}
	}
	
	@Override
	public void sendPluginMessage(DynamicGuiPlugin plugin, String channel, byte[] message) 
	{
		this.getPlayer().sendPluginMessage((Plugin) plugin, channel, message);
	}

	@Override
	public void playSound(String sound, Float volume, Float pitch) 
	{
		Player player = this.getPlayer();
		Location playerLocation = player.getLocation();
		player.playSound(playerLocation, Sound.valueOf(sound), volume, pitch);
	}

	@Override
	public void playEffect(String effect, int data) 
	{
		Player player = this.getPlayer();
		Location playerLocation = player.getLocation();
		playerLocation.getWorld().playEffect(playerLocation, Effect.valueOf(effect), data);
	}

	@Override
	public int getStatistic(Statistic statistic) 
	{
		return this.getPlayer().getStatistic(org.bukkit.Statistic.valueOf(statistic.getBukkitID()));
	}

	@Override
	public int getStatistic(Statistic statistic, String data)
	{
		if(data == null)
		{
			return -1;
		}
		String upperData = data.toUpperCase();

		try
		{
			if(StatisticType.MATERIAL == statistic.getStatisticType())
			{
				return this.getPlayer().getStatistic(org.bukkit.Statistic.valueOf(statistic.getBukkitID()), Material.valueOf(upperData));
			}
			else if(StatisticType.ENTITY == statistic.getStatisticType())
			{
				return this.getPlayer().getStatistic(org.bukkit.Statistic.valueOf(statistic.getBukkitID()), EntityType.valueOf(upperData));
			}
		}
		catch(IllegalArgumentException ex)
		{
			//Ignore and return -1
			return -1;
		}
		return -1;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateInventory() 
	{
		this.getPlayer().updateInventory();
	}
}