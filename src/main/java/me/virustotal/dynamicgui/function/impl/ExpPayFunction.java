package me.virustotal.dynamicgui.function.impl;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;

public class ExpPayFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2383762506458369815L;

	public ExpPayFunction(String name, String data) 
	{
		super(name, data);
	}
	
	public ExpPayFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		int amt;
		try
		{
			amt = Integer.parseInt(this.getData());
		}
		catch(Exception ex)
		{
			DynamicGUI.get().getLogger().info("Experience is set to an invalid number for data " + this.getData() + ", failing gracefully!");
			return false;
		}
		System.out.println("Exp: " + playerWrapper.getExperience());
		if(playerWrapper.getExperience() < amt)
		{
			return false;
		}
		
		playerWrapper.setExperience(playerWrapper.getExperience()- amt);
		return true;
		
	}
}