package me.virustotal.dynamicgui.economy.impl;

import me.virustotal.dynamicgui.economy.Economy;

public class VaultEconomy<P extends org.bukkit.entity.Player> implements Economy<P> {

	@Override
	public void setup() 
	{
		// TODO Auto-generated method stub
		
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