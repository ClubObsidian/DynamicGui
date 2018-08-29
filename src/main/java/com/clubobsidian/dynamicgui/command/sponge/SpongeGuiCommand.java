package com.clubobsidian.dynamicgui.command.sponge;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.sponge.SpongePlayerWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;

public class SpongeGuiCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
	{
		if(src instanceof Player)
		{
			Optional<String> gui = args.getOne("gui");
			if(gui.isPresent())
			{
				PlayerWrapper<?> playerWrapper = new SpongePlayerWrapper<Player>((Player) src);
				boolean executed = GuiManager.get().openGui(playerWrapper, gui.get());
				if(executed)
				{
					return CommandResult.success();
				}
			}
		}
		return CommandResult.empty();
	}
}