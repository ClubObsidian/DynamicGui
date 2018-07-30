package me.virustotal.dynamicgui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomCommandExecutor implements CommandExecutor {

	private String gui;
	private String alias;
	public CustomCommandExecutor(String gui, String alias)
	{

		this.gui = gui;
		this.alias = alias;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) 
	{
		if(label.equalsIgnoreCase(this.alias))
		{
			if(sender instanceof Player)
			{
				Player player = (Player)sender;
				player.chat("/gui " + this.gui);
				return true;
			}
		}
		return false;
	}
}