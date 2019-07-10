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
package com.clubobsidian.dynamicgui.listener.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.bukkit.BukkitPlayerWrapper;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.bukkit.BukkitInventoryWrapper;

public class InventoryCloseListener implements Listener {

	@EventHandler
	public void inventoryClose(InventoryCloseEvent e)
	{
		if(e.getPlayer() instanceof Player)
		{
			PlayerWrapper<Player> playerWrapper = new BukkitPlayerWrapper<Player>((Player) e.getPlayer());
			InventoryWrapper<Inventory> inventoryWrapper = new BukkitInventoryWrapper<Inventory>(e.getInventory());
			DynamicGui.get().getEventBus().callEvent(new com.clubobsidian.dynamicgui.event.inventory.InventoryCloseEvent(playerWrapper, inventoryWrapper));
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		PlayerWrapper<Player> playerWrapper = new BukkitPlayerWrapper<Player>(e.getPlayer());
		DynamicGui.get().getEventBus().callEvent(new com.clubobsidian.dynamicgui.event.player.PlayerQuitEvent(playerWrapper));
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e)
	{
		PlayerWrapper<Player> playerWrapper = new BukkitPlayerWrapper<Player>(e.getPlayer());
		DynamicGui.get().getEventBus().callEvent(new com.clubobsidian.dynamicgui.event.player.PlayerKickEvent(playerWrapper));
	}
}