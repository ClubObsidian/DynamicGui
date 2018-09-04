package com.clubobsidian.dynamicgui.command.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.bukkit.BukkitPlayerWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;

public class CustomCommandExecutor implements CommandExecutor {

	private String gui;
	public CustomCommandExecutor(String gui)
	{

		this.gui = gui;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) 
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			PlayerWrapper<?> playerWrapper = new BukkitPlayerWrapper<>(player);
			return GuiManager.get().openGui(playerWrapper, this.gui);
		}
		return false;
	}
}