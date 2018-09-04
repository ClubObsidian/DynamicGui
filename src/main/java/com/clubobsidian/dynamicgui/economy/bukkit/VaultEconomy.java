package com.clubobsidian.dynamicgui.economy.bukkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.economy.Economy;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;

public class VaultEconomy implements Economy {

	private Object economy;
	private Class<?> economyClass;
	private Method getBalanceMethod;
	private Method withdrawPlayerMethod;
	private Method depositPlayerMethod;
	
	@Override
	public boolean setup() 
	{
		Plugin vault = Bukkit.getServer().getPluginManager().getPlugin("Vault");
		if(vault == null)
		{
			return false;
		}
		
		try 
		{
			economyClass = Class.forName("net.milkbowl.vault.economy.Economy");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		if(this.economyClass == null)
		{
			return false;
		}
		
		this.economy = Bukkit.getServer().getServicesManager().getRegistration(this.economyClass).getProvider();
		return this.economy != null;
	}

	@Override
	public BigDecimal getBalance(PlayerWrapper<?> playerWrapper) 
	{
		double balance = -1;
		if(this.getBalanceMethod == null)
		{
			try 
			{
				this.getBalanceMethod = this.economyClass.getDeclaredMethod("getBalance", OfflinePlayer.class);
				this.getBalanceMethod.setAccessible(true);
			} 
			catch (NoSuchMethodException | SecurityException e) 
			{
				e.printStackTrace();
			}
		}
		
		try 
		{
			balance = (double) this.getBalanceMethod.invoke(this.economy, playerWrapper.getPlayer());
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
		}
		
		return new BigDecimal(balance);
	}

	@Override
	public boolean withdraw(PlayerWrapper<?> playerWrapper, BigDecimal amt) 
	{
		if(amt.doubleValue() < 0)
			return false;
		
		if(this.withdrawPlayerMethod == null)
		{
			try 
			{
				this.withdrawPlayerMethod = this.economyClass.getDeclaredMethod("withdrawPlayer", OfflinePlayer.class, double.class);
				this.withdrawPlayerMethod.setAccessible(true);
			} 
			catch (NoSuchMethodException | SecurityException e) 
			{
				e.printStackTrace();
			}
		}
		
		double amtDouble = amt.doubleValue();
		double balance = this.getBalance(playerWrapper).doubleValue();
		
		if(balance >= amtDouble)
		{
			try 
			{
				this.withdrawPlayerMethod.invoke(this.economy, playerWrapper.getPlayer(), amtDouble);
				return true;
			} 
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
			{
				e.printStackTrace();
			}
		}

		return false;
	}

	@Override
	public boolean deposit(PlayerWrapper<?> playerWrapper, BigDecimal amt) 
	{
		if(amt.doubleValue() < 0)
			return false;
		
		if(this.depositPlayerMethod == null)
		{
			try 
			{
				this.depositPlayerMethod = this.economyClass.getDeclaredMethod("depositPlayer", OfflinePlayer.class, double.class);
				this.depositPlayerMethod.setAccessible(true);
			} 
			catch (NoSuchMethodException | SecurityException e) 
			{
				e.printStackTrace();
			}
		}
		
		try 
		{
			this.depositPlayerMethod.invoke(this.economy, amt.doubleValue());
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
		}
		return true;
	}
}