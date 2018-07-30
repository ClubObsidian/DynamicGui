package me.virustotal.dynamicgui.economy.impl;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import me.virustotal.dynamicgui.economy.Economy;

public class VaultEconomy<P extends org.bukkit.entity.Player> implements Economy<P> {

	private Object economy;
	
	@Override
	public boolean setup() 
	{
		Plugin vault = Bukkit.getServer().getPluginManager().getPlugin("Vault");
		if(vault == null)
		{
			return false;
		}
		
		Class<?> economyClass = null;
		try 
		{
			economyClass = Class.forName("net.milkbowl.vault.economy.Economy");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		if(economyClass == null)
		{
			return false;
		}
		
		this.economy = Bukkit.getServer().getServicesManager().getRegistration(economyClass).getProvider();
		return economy != null;
	}
	
	@Override
	public boolean withdraw(P player) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deposit(P player) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getBalance(P player) 
	{
	
		return 0;
	}
}