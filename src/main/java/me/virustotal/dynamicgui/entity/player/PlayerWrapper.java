package me.virustotal.dynamicgui.entity.player;

import java.util.UUID;

import me.virustotal.dynamicgui.inventory.InventoryWrapper;

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
	
	
}