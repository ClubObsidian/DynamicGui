package com.clubobsidian.dynamicgui.entity.player;

import java.util.UUID;

import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.plugin.DynamicGUIPlugin;
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
	public abstract void sendPluginMessage(DynamicGUIPlugin plugin, String channel, byte[] message);
	public abstract void playSound(String sound, Float volume, Float pitch);
	public abstract void playEffect(String effect, int data);
	public abstract int getStatistic(Statistic statistic);
	public abstract int getStatistic(Statistic statistic, String data);

	public abstract void updateInventory();
		
}