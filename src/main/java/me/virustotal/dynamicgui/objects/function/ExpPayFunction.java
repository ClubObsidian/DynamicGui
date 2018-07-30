package me.virustotal.dynamicgui.objects.function;

import java.util.logging.Level;

import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.objects.Function;

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
	public boolean function(Player player)
	{
		int amt;
		try
		{
			amt = Integer.parseInt(this.getData());
		}
		catch(Exception ex)
		{
			DynamicGUI.getPlugin().getLogger().log(Level.INFO, "Experience is set to an invalid number for data " + this.getData() + ", failing gracefully!");
			return false;
		}
		if(player.getTotalExperience() < amt)
		{
			return false;
		}
		
		player.setTotalExperience(player.getTotalExperience() - amt);
		return true;
		
	}

}
