package com.clubobsidian.dynamicgui.command.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.player.bukkit.BukkitPlayerWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;

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
				return GuiManager.get().openGUI(playerWrapper, args[0]);
			}
		}
		return false;
	}
}
