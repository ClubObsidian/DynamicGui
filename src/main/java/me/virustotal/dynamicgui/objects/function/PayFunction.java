package me.virustotal.dynamicgui.objects.function;

import java.math.BigDecimal;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.economy.Economy;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.objects.Function;
import me.virustotal.dynamicgui.plugin.impl.BukkitPlugin;
import me.virustotal.dynamicgui.util.server.ServerType;
import me.virustotal.dynamicgui.util.server.ServerUtil;

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
	public boolean function(final PlayerWrapper<?> player)
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
		if(DynamicGUI.getInstance().getPlugin().getEconomy() == null)
			return false;
		
		
		if(DynamicGUI.getInstance().getPlugin().getEconomy().getBalance(player) < amt)
		{
			return false;
		}

		//DynamicGUI.getPlugin().getEconomy().withdrawPlayer(player.getName(), amt);
		return true;
	}
}
