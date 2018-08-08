package me.virustotal.dynamicgui.command.sponge;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import me.virustotal.dynamicgui.command.GUIExecutor;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.entity.player.sponge.SpongePlayerWrapper;

public class SpongeGUICommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
	{
		if(src instanceof Player)
		{
			Optional<String> gui = args.getOne("gui");
			if(gui.isPresent())
			{
				PlayerWrapper<?> playerWrapper = new SpongePlayerWrapper<Player>((Player) src);
				boolean executed = new GUIExecutor().execute(playerWrapper, gui.get());
				if(executed)
				{
					return CommandResult.success();
				}
			}
		}
		return CommandResult.empty();
	}
}