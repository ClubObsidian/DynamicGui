package me.virustotal.dynamicgui.entity.player.impl;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.impl.BukkitInventoryWrapper;

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
	public InventoryWrapper<Inventory> getOpenInventory() 
	{
		InventoryView openInventory = this.getPlayer().getOpenInventory();
		if(openInventory == null)
		{
			return null;
		}
		return new BukkitInventoryWrapper<Inventory>(openInventory.getTopInventory());
	}



}