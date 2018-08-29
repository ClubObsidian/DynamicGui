package com.clubobsidian.dynamicgui.command.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.bukkit.BukkitPlayerWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;

public class BukkitGuiCommand2 implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(sender instanceof Player)
		{
			if(args.length == 1)
			{
				Player player = (Player) sender;
				PlayerWrapper<?> playerWrapper = new BukkitPlayerWrapper<Player>(player);
				return GuiManager.get().openGui(playerWrapper, args[0]);
			}
		}
		return false;
	}
}
