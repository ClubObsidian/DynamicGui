package me.virustotal.dynamicgui.objects.function;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
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
	public boolean function(final PlayerWrapper<?> player)
	{
		final String finalData = this.getData();

		if(player.getOpenInventoryWrapper() != null)
			player.closeInventory();
		
		DynamicGUI.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(DynamicGUI.getInstance().getPlugin(), new Runnable()
		{
			@Override
			public void run()
			{
				player.chat("/gui " + finalData);
			}
		},2L);
		return true;
	}
}
