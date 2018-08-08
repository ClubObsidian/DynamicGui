package me.virustotal.dynamicgui.command.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.command.GUIExecutor;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.entity.player.bukkit.BukkitPlayerWrapper;

public class BukkitGUICommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(sender instanceof Player)
		{
			if(args.length == 1)
			{
				Player player = (Player) sender;
				PlayerWrapper<?> playerWrapper = new BukkitPlayerWrapper<Player>(player);
				return new GUIExecutor().execute(playerWrapper, args[0]);
			}
		}
		return false;
	}
}
