package me.virustotal.dynamicgui.entity.player.impl;

import java.util.UUID;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.impl.BukkitInventoryWrapper;
import me.virustotal.dynamicgui.plugin.DynamicGUIPlugin;

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
	public void sendPluginMessage(DynamicGUIPlugin<?, ?> plugin, String channel, byte[] message) 
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
}