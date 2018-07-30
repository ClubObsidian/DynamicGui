package me.virustotal.dynamicgui.objects.function;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.objects.Function;

public class GuiFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 848178368629667482L;

	public GuiFunction(String name, String data) 
	{
		super(name,data);
	}
	
	public GuiFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(Player player)
	{
		final String finalData = this.getData();
		final Player finalPlayer = player;
		
		if(player.getOpenInventory() != null)
			player.closeInventory();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(DynamicGUI.getPlugin(), new Runnable()
		{
			@Override
			public void run()
			{
				finalPlayer.chat("/gui " + finalData);
			}
		},2L);
		return true;
	}
}
