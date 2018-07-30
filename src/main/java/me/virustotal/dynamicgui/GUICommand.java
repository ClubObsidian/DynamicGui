package me.virustotal.dynamicgui;

import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.objects.SoundWrapper;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GUICommand implements CommandExecutor {

	private DynamicGUI plugin;
	public GUICommand(DynamicGUI plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{	
		if(label.equalsIgnoreCase("gui"))
		{
			if(args.length == 1)
			{
				GUI gui = null;
				
				if(GuiApi.hasGuiName(args[0]))
					gui = GuiApi.getGuiByName(args[0]);

				if(gui == null)
				{
					sender.sendMessage(this.plugin.getNoGui());
				} 
				else if(sender instanceof Player)
				{
					final Player player = (Player)sender;
					boolean permNull = (gui.getPermission() == null);
					if(!permNull)
					{
						if(!player.hasPermission(gui.getPermission()))
						{
							if(gui.getpMessage() == null)
								player.sendMessage(this.plugin.getNoPermissionGui());
							else
								player.sendMessage(gui.getpMessage());

							return true;
						}
					}
					gui.buildInventory(player);//TODO
					
					for(SoundWrapper wrapper : gui.getOpeningSounds())
					{
						wrapper.playSoundToPlayer(player); 
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(DynamicGUI.getPlugin(), new Runnable()
					{
						@Override
						public void run()
						{
							player.updateInventory();
						}
					},2L);					
					this.plugin.playerGuis.put(player.getName(), gui);
				}
				return true;
			}
		}

		return false;
	}
}
