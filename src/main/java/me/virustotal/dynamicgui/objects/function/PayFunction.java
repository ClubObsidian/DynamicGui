package me.virustotal.dynamicgui.objects.function;

import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.objects.Function;

public class PayFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8941864727381394744L;

	public PayFunction(String name, String data) 
	{
		super(name,data);
	}

	public PayFunction(String name) 
	{
		super(name);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean function(final Player player)
	{
		double amt;
		try
		{
			amt = Double.parseDouble(this.getData());
		}
		catch(Exception ex)
		{ 
			ex.printStackTrace();
			return false;
		}
		if(DynamicGUI.getInstance().getEconomy() == null)
			return false;
		if(DynamicGUI.getInstance().getEconomy().getBalance(player.getName()) < amt)
		{
			return false;
		}

		DynamicGUI.getInstance().getEconomy().withdrawPlayer(player.getName(), amt);
		return true;
	}
}
